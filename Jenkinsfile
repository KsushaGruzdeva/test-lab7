pipeline {
    agent any
    
    tools {
        maven 'Maven'
        jdk 'jdk17'
    }
    
    stages {
        stage('Получение кода и сборка') {
            steps {
                git branch: 'main', url: 'https://github.com/KsushaGruzdeva/test-lab7.git'
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