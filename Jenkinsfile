@Library('MyTestLibrary') _

pipeline {
	parameters {
		choice choices: ['none', 'dna-client-library', 'dna-trusted-library', 'depotStream'], description: 'Choose which project to be released', name: 'project'
        choice choices: ['none', 'cpp', 'unity', 'unreal', 'mainline'], description: 'Engine name', name: 'engine'
		string( name: 'releaseVersion',  defaultValue: '', description: 'release version to be released')
        string( name: 'buildVersion',  defaultValue: '', description: 'build version to be released')
        string( name: 'releaseDescription',  defaultValue: '', description: 'Description of release stream')
        string( name: 'releaseTitle',  defaultValue: '', description: 'release title')
        booleanParam('preRelease')
        booleanParam('hotfix')
	}
    options {
        skipDefaultCheckout()
    }
    agent any
    stages {
/*        stage('Clean up') {
            steps {
                script{
                    dir("${env.WORKSPACE}"){
                        sh """
                        echo @===================Cleaning up===================@
                            find . -mindepth 1 -delete
                        """
                    }
                }
            }
        }
*/
       stage('Clean up') {
            steps {
                script{
                    cleanWs()
                }
            }
        }
        stage('Checkout scm') {
            steps {
                checkout scm
            }
        }
/*        stage('Print string') {
            steps {
                println "test"
                sh """
                    cat dummy
                    echo ${env.CHANGE_FORK}
                    echo ${params.preRelease}
                """
                script{
                    if (params.hotfix != true && params.preRelease != true){
                        println "helloWorld"
                    }
                
                    // Determine the tag and title based on preRelease parameter
                    def tagPrefix = params.preRelease == true ? "pre-" : ""
                    def preReleaseFlag = params.preRelease == true ? "--pre-release" : ""

                    println "@==========Create tag==========@"
                    sh"""
                        echo "tag name: ${tagPrefix}${params.releaseVersion}-${params.buildVersion}"
                    """

                    println "@==========Create github release==========@"
                    sh"""
                        echo "release title : ${tagPrefix}${params.releaseTitle}"
                    """
                }
            }
        }
*/
        stage('Release to Github') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-personal-pat', passwordVariable: 'GITHUB_TOKEN', usernameVariable: '')]) {
                    script{
                        // Determine the tag and title based on preRelease parameter
                        def tagPrefix = params.preRelease == true ? "pre-" : ""
                        def preReleaseFlag = params.preRelease == true ? "--pre-release" : ""

                        println "@==========Setup Git==========@"
                        sh """
                        git config user.email "islamifauzi@gmail.com"
                        git config user.name "fauzislami"
                        """
                        if (params.hotfix != true && params.preRelease != true){
                            println "@==========Create release branch==========@"
                            sh"""
                            git branch release-${params.releaseVersion}
                            git push origin -u release-${params.releaseVersion}
                            git status                    
                            """
                        }

                        println "@==========Create tag==========@"
                        sh"""
                        git tag -a ${tagPrefix}${params.releaseVersion}-${params.buildVersion} -m "${params.releaseDescription}"
                        git push origin ${tagPrefix}${params.releaseVersion}-${params.buildVersion}
                        """

                        println "@==========Create github release==========@"
                        sh"""
                        curl -L https://github.com/github-release/github-release/releases/download/v0.10.0/linux-amd64-github-release.bz2 -o /tmp/gh-release.bz2
                        bzip2 -d /tmp/gh-release.bz2
                        chmod +x /tmp/gh-release
                        /tmp/gh-release release -u fauzislami -r release-test --tag ${tagPrefix}${params.releaseVersion}-${params.buildVersion} --name "${params.releaseTitle}" --description "${params.releaseDescription} ${preReleaseFlag}"
                        rm -f /tmp/gh-release
                        """


                        // echo "@==========Create tag==========@"
                        // git config --global --replace-all url.https://${GITHUB_TOKEN}:x-oauth-basic@github.com/fauzislami/.insteadOf https://github.com/fauzislami/
                        // git tag -a ${params.releaseVersion} -m "${params.releaseDescription}" release-${params.releaseVersion}
                        // git push origin ${params.releaseVersion}
                        // """
                    }
                }
            }
        }       
        stage('Release to Perforce') {
            steps{
                script{
                    println "release to perforce"
                    // // def parentStream = "//${params.project}/${params.engine}"
                    // def parentStream = "//${params.project}/mainline"
                    // def childStream = "//${params.project}/" + "${params.engine}" + "-" + "${params.releaseVersion}"
                    // def createNewStream = p4Release(
                    //     parentStream: parentStream,
                    //     childStream: childStream,
                    //     p4Creds: "perforce-local",
                    // )

                    // if(createNewStream.isFailed){
                    //     println("Stream is not created due to an error:\n\n${createNewStream.error}")
                    //     error()
                    // }
                }
            }
        }
    }
}