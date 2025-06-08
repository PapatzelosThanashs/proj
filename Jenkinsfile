def frontendImage = null
def backendImage = null
def dbImage = null


pipeline {
     agent none

    environment {
        NEXUS_REGISTRY = 'host.docker.internal:5000'   // Your private Nexus Docker registry URL (host:port)
        DOCKER_CREDS_ID = 'nexus-docker-creds'           // Jenkins credential ID for Nexus Docker registry
        //GIT_REPO_URL = 'https://github.com/PapatzelosThanashs/proj.git'
        //GIT_BRANCH = 'master'
        //GIT_CREDENTIALS_ID = 'github-creds'      
    }

    stages {

        stage('Initialize') {
            agent { label 'jenkins-agent' }
            steps {
                script {
                    IMAGE_TAG = new Date().format("yyyy-MM-dd_HHmm", TimeZone.getTimeZone('UTC'))
                    echo "Using IMAGE_TAG: ${IMAGE_TAG}"
                }
            }
        }
        
            stage('Checkout') {
                agent { label 'jenkins-agent' }
                steps {
                    // Checkout GitHub repo (private or public)
                    git(
                        url: 'https://github.com/PapatzelosThanashs/proj.git',
                        branch: 'master'
                        // credentialsId: "${GIT_CREDENTIALS_ID}"
                    )
                    stash name: 'source-code'   
                }
            }
 
        stage('Build All Images') {
            parallel {
                stage('Build Frontend') {
                agent { label 'jenkins-agent' }
                    when {
                        changeset "**/frontend/**"
                    }
                    steps {
                        unstash 'source-code'
                        dir('frontend') {
                            sh 'npm install && npm run build'
                            script {
                                frontendImage = docker.build("${NEXUS_REGISTRY}/frontend:${IMAGE_TAG}")
                                frontendImage.tag('latest')

                            }
                        }
                    }
                }

                stage('Build Backend') {
                    agent { label 'jenkins-agent' }
                    when {
                        changeset "**/demo/**"
                    }
                    steps {
                        unstash 'source-code'
                        dir('demo') {
                            sh 'mvn clean package'
                            script {
                                backendImage = docker.build("${NEXUS_REGISTRY}/backend:${IMAGE_TAG}")
                                backendImage.tag('latest')

                            }
                        }
                    }
                }

                stage('Build DB') {
                    agent { label 'jenkins-agent' }
                    when {
                        changeset "**/database/**"
                    }
                    steps {
                        unstash 'source-code'
                        dir('database') {
                            script {
                                dbImage = docker.build("${NEXUS_REGISTRY}/db:${IMAGE_TAG}")
                                dbImage.tag('latest')

                            }
                        }
                    }
                }
            }
        }

        stage('Push Images') {
            parallel {
                stage('Push Frontend') {
                    agent { label 'jenkins-agent' }
                    when {
                        expression { frontendImage != null }
                    }
                    steps {
                        script {
                                docker.withRegistry("http://${NEXUS_REGISTRY}", "${DOCKER_CREDS_ID}") {
                                frontendImage.push("${IMAGE_TAG}")
                                frontendImage.push("latest")
                            }
                        }
                    }
                }

                stage('Push Backend') {
                    agent { label 'jenkins-agent' }
                    when {
                        expression { backendImage != null }
                    }
                    steps {
                        script {
                                docker.withRegistry("http://${NEXUS_REGISTRY}", "${DOCKER_CREDS_ID}") {
                                backendImage.push("${IMAGE_TAG}")
                                backendImage.push("latest")
                            }
                        }
                    }
                }

                stage('Push DB') {
                    agent { label 'jenkins-agent' }
                    when {
                        expression { dbImage != null }
                    }
                    steps {
                        script {
                                docker.withRegistry("http://${NEXUS_REGISTRY}", "${DOCKER_CREDS_ID}") {
                                dbImage.push("${IMAGE_TAG}")
                                dbImage.push("latest")
                            }
                        }
                    }
                }
            }
        }
    }

}
