// Jenkins file for Windows machine
node {
    stage('SCM Checkout'){
        git branch: 'selenium_testing', credentialsId: '8280ff9f-7c61-4212-8afd-6a966bafd4b5', url: 'https://github.com/Jaykingamez/mockchain'
    }
    stage('Mvn Package'){
        def mvnHome = tool name: 'maven 3.8.2', type: 'maven'
        def mvnCMD = "${mvnHome}/bin/mvn"
        bat "${mvnCMD} clean package"
    }
    stage('Build docker'){
        bat 'docker build -t jaykingamez/mockchain:0.0.1 .'
    } 
    stage('Push Docker Image'){	
        withCredentials([string(credentialsId: 'dockerPWD', variable: 'dockerPWD')]) {
            bat "docker login -u jaykingamez -p ${dockerPWD}"
        }
        bat 'docker push jaykingamez/mockchain:0.0.1'
	    
    }
}