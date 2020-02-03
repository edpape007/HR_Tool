pipeline {
    agent any
    tools {
        maven 'Local Maven'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests=true'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deployment') {
            steps {
                withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                    sh 'echo "Shuttingdown app..."'
                    sh 'mvn spring-boot:stop'
                    sh 'echo "Starting app..."'
                    sh 'mvn spring-boot:start'
                }
            }
        }
    }
}