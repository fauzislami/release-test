/* 
mmP4Stream(
  parentStream: "//core/perforcetest-fauzi",
  childStream: "//core/perforcetest-fauzi-unreal-v0.1.0"
)
*/
def call(LinkedHashMap config) { 
  def p4Object = [:]
  try{
    p4creds = '4e7ac971-ac41-4a92-8914-c42f8bfd785e'
    
    // p4 definition
    def p4def = p4(
      credential: p4creds, 
      workspace: [
        $class: 'StreamWorkspaceImpl', 
        charset: 'none', 
        format: 'jenkins-${NODE_NAME}-${JOB_NAME}-${EXECUTOR_NUMBER}', 
        pinHost: false, 
        streamName: config.parentStream
      ]
    )

    // create new stream with release type
    def runp4 = p4def.run('stream', '-ov', '-t','release','-P',config.parentStream,config.childStream)
    
    runp4.each { i -> 
      // changed the policy to "no access to parent & from parent"
      i.putAt('Options','allsubmit unlocked notoparent nofromparent mergedown')  
      
      // apply the stream spec
      p4def.save('stream',i)
    }

    p4Object.isFailed = false
    return p4Object
  } catch(e){
    def p4error = e.toString()
    println p4error
    p4Object.isFailed = true
    p4Object.error = p4error
    return p4Object
  } 
}