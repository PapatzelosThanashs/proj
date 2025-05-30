def frontendImage = null
def backendImage = null
def dbImage = null


pipeline {
    agent { label 'jenkins-agent' }
    environment {
        NEXUS_REGISTRY = 'nexus:5000'   // Your private Nexus Docker registry URL (host:port)
        IMAGE_TAG = null
        DOCKER_CREDS_ID = 'nexus-docker-creds'           // Jenkins credential ID for Nexus Docker registry
        GIT_REPO_URL = 'https://github.com/PapatzelosThanashs/proj.git'
        GIT_BRANCH = 'master'
        //GIT_CREDENTIALS_ID = 'github-creds'      
    }

    stages {

        stage('Initialize') {
            steps {
                script {
                    IMAGE_TAG = new Date().format("yyyy-MM-dd_HHmm", TimeZone.getTimeZone('UTC'))
                    echo "Using IMAGE_TAG: ${IMAGE_TAG}"
                }
            }
        }
        
            stage('Checkout') {
            steps {
                // Checkout GitHub repo (private or public)
                checkout([$class: 'GitSCM',
                    branches: [[name: "*/${GIT_BRANCH}"]],
                    userRemoteConfigs: [[
                        url: "${GIT_REPO_URL}",
                        //credentialsId: "${GIT_CREDENTIALS_ID}" // Remove if public repo
                    ]]
                ])
            }
        }
 
        stage('Build All Images') {
            parallel {
                stage('Build Frontend') {
                    when {
                        changeset "**/frontend/**"
                    }
                    steps {
                        dir('frontend') {
                            sh 'npm install && npm run build'
                            script {
                                frontendImage = docker.build("${NEXUS_REGISTRY}/frontend:${IMAGE_TAG}")
                            }
                        }
                    }
                }

                stage('Build Backend') {
                    when {
                        changeset "**/demo/**"
                    }
                    steps {
                        
                        dir('demo') {
                            sh 'mvn clean package'
                            script {
                                backendImage = docker.build("${NEXUS_REGISTRY}/backend:${IMAGE_TAG}")
                            }
                        }
                    }
                }

                stage('Build DB') {
                    when {
                        changeset "**/database/**"
                    }
                    steps {
                        dir('database') {
                            script {
                                dbImage = docker.build("${NEXUS_REGISTRY}/db:${IMAGE_TAG}")
                            }
                        }
                    }
                }
            }
        }
        stage('Push Images') {
                steps {
                    script {
                        docker.withRegistry("http://${NEXUS_REGISTRY}", "${DOCKER_CREDS_ID}") {
                            if (frontendImage != null) {
                                frontendImage.push()
                            }
                            if (backendImage != null) {
                                backendImage.push()
                            }
                            if (dbImage != null) {
                                dbImage.push()
                            }
                        }
                    }
                }
        }

    }

    post {
        always {
            cleanWs() // Cleans workspace
        }
    }
}
