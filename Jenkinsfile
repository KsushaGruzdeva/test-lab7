pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/KsushaGruzdeva/test-lab7.git'
            }
        }
        
        stage('Build & Test') {
            steps {
                dir('.') {  // Переходим в корень проекта
                    sh 'pwd'  // Должно показать корень
                    sh 'ls -la'  // Проверяем файлы
                    sh 'mvn clean test -Dtest="*IntegrationTest"'
                }
            }
        }
    }
}