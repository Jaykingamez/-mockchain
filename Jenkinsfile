pipeline {
    // use repo's dockerfile
    agent { 
        dockerfile true 
    }
    stages{
        stage('SCM Checkout'){
            steps{
                git branch: 'selenium_testing', credentialsId: '8280ff9f-7c61-4212-8afd-6a966bafd4b5', url: 'https://github.com/Jaykingamez/mockchain'
            }
        }
        stage('Mvn Package'){
            steps{
                script{
                    def mvnHome = tool name: 'maven 3.8.2', type: 'maven'
                    def mvnCMD = "${mvnHome}/bin/mvn"
                    bat "${mvnCMD} clean package"
                }
            }
        }
        stage('Build docker'){
            steps{
                bat 'docker build -t jaykingamez/mockchain:0.0.1 .'
            }
        } 
    }
}