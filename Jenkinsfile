// Jenkins file for Windows machine
node {
    stage('Build docker'){
        ws("C:/Users/jayki/Documents/workspace") {
            bat 'docker build -t jaykingamez/mockchain:0.0.1 .'
        }
    } 
    stage('Push Docker Image'){	
        ws("C:/Users/jayki/Documents/workspace") {
            withCredentials([string(credentialsId: 'dockerPWD', variable: 'dockerPWD')]) {
            bat "docker login -u jaykingamez -p ${dockerPWD}"
            }
            bat 'docker push jaykingamez/mockchain:0.0.1'
        }
    }
    stage("Remove previous container"){
        def dockerStop = 'docker stop mockchain'
        def dockerRemove = 'docker rm mockchain'
        sshagent(['dev-server']) {
            try{
                // stop image instance first
                bat "ssh -o StrictHostKeyChecking=no ec2-user@18.141.187.89 ${dockerStop}"
                // remove docker image if it exist
                bat "ssh -o StrictHostKeyChecking=no ec2-user@18.141.187.89 ${dockerRemove}"
            }       
            catch (err){
                echo "Previous Image does not exist"
            }
        }
    }
    stage('Run Container on AWS'){
        // get newest docker Image
        def newDockerImage = 'docker pull jaykingamez/mockchain:0.0.1'
        def dockerRun = 'docker run -p 8080:8080 -d --name mockchain jaykingamez/mockchain:0.0.1'
        sshagent(['dev-server']) {
            // replace with new one
            bat "ssh -o StrictHostKeyChecking=no ec2-user@18.141.187.89 ${newDockerImage}"
            bat "ssh -o StrictHostKeyChecking=no ec2-user@18.141.187.89 ${dockerRun}"
        }
    }
}