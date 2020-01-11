pipeline {
    agent any
    tools {
        maven 'M3'
    }

    stages {
        stage('Build & Run Tests') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deployment') {
            steps {
                sh 'mvn spring-boot:run'
            }
        }
    }
}