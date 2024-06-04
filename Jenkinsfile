@Library('MyTestLibrary') _

pipeline {
	parameters {
		choice choices: ['none', 'dna-client-library', 'dna-trusted-library', 'depotStream'], description: 'Choose which project to be released', name: 'project'
        choice choices: ['none', 'cpp', 'unity', 'unreal', 'mainline'], description: 'Engine name', name: 'engine'
		string( name: 'releaseVersion',  defaultValue: '', description: 'Version to be released')
        string( name: 'releaseDescription',  defaultValue: '', description: 'Description of release stream')
	}
    options {
        skipDefaultCheckout()
    }
    agent any
    stages {
        stage('Clean up') {
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
        stage('Checkout scm') {
            steps {
                checkout scm
            }
        }
        stage('Release to Github') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-personal-pat', passwordVariable: 'GITHUB_TOKEN', usernameVariable: '')]) {
                    sh """
                    git config user.email "islamifauzi@gmail.com"
                    git config user.name "fauzislami"

                    echo "@==========Create tag==========@"
                    git config --global --replace-all url.https://${GITHUB_TOKEN}:x-oauth-basic@github.com/fauzislami/.insteadOf https://github.com/fauzislami/
                    git tag -a ${params.releaseVersion} -m "${params.releaseDescription}"
                    git push origin ${params.releaseVersion}
                    """
                }
            }
        }
        stage('Release to Perforce') {
            steps{
                script{
                    // def parentStream = "//${params.project}/${params.engine}"
                    def parentStream = "//${params.project}/mainline"
                    def childStream = "//${params.project}/" + "${params.engine}" + "-" + "${params.releaseVersion}"
                    def createNewStream = p4Release(
                        parentStream: parentStream,
                        childStream: childStream,
                        p4Creds: "perforce-local",
                        p4Desc: "${params.releaseDescription}"
                    )

                    if(createNewStream.isFailed){
                        println("Stream is not created due to an error:\n\n${createNewStream.error}")
                    }
                }
            }
        }
    }
}