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
                bat 'echo "Shuttingdown app..."'
                bat 'curl -X POST http://localhost:9090/actuator/shutdown'
                bat 'echo "Starting app..."'
                bat 'mvn spring-boot:start'
            }
        }
    }
}