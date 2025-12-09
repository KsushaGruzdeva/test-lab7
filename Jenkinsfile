pipeline {
    agent any
    
    tools {
        maven 'Maven'
        jdk 'jdk17'
    }
    
    stages {
        stage('Получение кода и сборка') {
            steps {
                git branch: 'main', url: 'https://github.com/ваш-user/task-manager-lab.git'
                sh 'mvn clean compile'
            }
        }
        
        stage('Интеграционные тесты') {
            steps {
                sh 'mvn test -Dtest="*IntegrationTest"'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}