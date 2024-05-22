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
                withCredentials([gitUsernamePassword(credentialsId: 'ssh-personal-github')]) {
                    sh """
                    git tag -a v0.3.0 -m "test git tag release"
                    """
                }
            }
        }
    }
}