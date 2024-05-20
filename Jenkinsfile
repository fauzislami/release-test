@Library('MyTestLibrary') _

pipeline {
    agent any

    stages {
        stage('Release to Perforce') {
            steps{
                script{
                    def childStream = "//" + "unreal" + "-" + "v0.1.0"
                    def parentStream = "//depotStream/mainline"
                    def createNewStream = sdkRelease(
                        parentStream: parentStream,
                        childStream: childStream
                    )

                    if(createNewStream.isFailed){
                        println("Stream is not created due to an error:\n\n${createNewStream.error}")
                    }
                }
            }
        }
        stage('Release to Github') {
            steps {
                echo "test"
            }
        }
    }
}