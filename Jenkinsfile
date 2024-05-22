@Library('MyTestLibrary') _

pipeline {
	parameters {
		choice choices: ['none', 'dna-client-library', 'dna-trusted-library', 'depotStream'], description: 'Choose which project to be released', name: 'project'
        choice choices: ['none', 'cpp', 'unity', 'unreal', 'mainline'], description: 'Engine name', name: 'engine'
		string( name: 'releaseVersion',  defaultValue: '', description: 'Version to be released')
	}
    agent any

    stages {
        stage('Release to Github') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-personal-pat', passwordVariable: 'GITHUB_TOKEN', usernameVariable: '')]) {
                    sh """
                    git config user.email "islamifauzi@gmail.com"
                    git config user.name "fauzislami"

                    echo "@==========Create tag==========@"
                    git config --global --replace-all url.https://${GITHUB_TOKEN}:x-oauth-basic@github.com/fauzislami/.insteadOf https://github.com/fauzislami/
                    git tag -a ${params.releaseVersion} -m "test git tag"
                    git push origin ${params.releaseVersion}
                    """
                }
            }
        }
        stage('Release to Perforce') {
            steps{
                script{
                    def parentStream = "//${params.project}/${params.engine}"
                    def childStream = "//${params.project}/" + "${params.engine}" + "-" + "${params.releaseVersion}"
                    def createNewStream = p4Release(
                        parentStream: parentStream,
                        childStream: childStream,
                        p4creds: "perforce-local"
                    )

                    if(createNewStream.isFailed){
                        println("Stream is not created due to an error:\n\n${createNewStream.error}")
                    }
                }
            }
        }
    }
}