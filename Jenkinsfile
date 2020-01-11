pipeline {
    agent any
    tools {
        maven 'Local Maven'
    }

    stages {
        stage('Build & Run Tests') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Deployment') {
            steps {
                bat 'nohup mvn spring-boot:run'
            }
        }
    }
}