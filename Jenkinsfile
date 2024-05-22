@Library('MyTestLibrary') _

pipeline {
    agent any

    stages {
        // stage('Release to Perforce') {
        //     steps{
        //         script{
        //             def parentStream = "//depotStream/mainline"
        //             def childStream = "//depotStream/" + "unreal" + "-" + "v0.3.0"
        //             def createNewStream = p4Release(
        //                 parentStream: parentStream,
        //                 childStream: childStream,
        //                 p4creds: "perforce-local"
        //             )

        //             if(createNewStream.isFailed){
        //                 println("Stream is not created due to an error:\n\n${createNewStream.error}")
        //             }
        //         }
        //     }
        // }
        stage('Release to Github') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'github-personal-pat', passwordVariable: 'GITHUB_TOKEN', usernameVariable: '')]) {
                    sh """
                    git config user.email "islamifauzi@gmail.com"
                    git config user.name "fauzislami"

                    echo "@==========Create tag"==========@"
                    git config --global --replace-all url.https://${GITHUB_TOKEN}:x-oauth-basic@github.com/fauzislami/.insteadOf https://github.com/fauzislami/
                    git tag -a v0.3.0 -m "test git tag release"
                    """
                }
            }
        }
    }
}