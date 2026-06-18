pipeline {
    agent any

    environment {
        FIREBASE_TOKEN        = credentials('FIREBASE_TOKEN')
        FIREBASE_APP_ID       = credentials('FIREBASE_APP_ID')
        FIREBASE_TESTER_GROUP = credentials('FIREBASE_TESTER_GROUP')
        ANDROID_HOME          = "/Users/kumargaurav/Library/Android/sdk"
        PATH                  = "/Users/kumargaurav/.rbenv/shims:/Users/kumargaurav/.rbenv/bin:/usr/local/bin:/opt/homebrew/bin:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:/usr/bin:/bin"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'master',
                    credentialsId: 'github-ssh-key',
                    url: 'git@github.com:gauravgupta188/ExpenseTracker.git'
            }
        }

        stage('Setup Ruby') {
            steps {
                sh '''
                    export PATH="/Users/kumargaurav/.rbenv/shims:/Users/kumargaurav/.rbenv/bin:$PATH"
                    export RBENV_ROOT="/Users/kumargaurav/.rbenv"
                    eval "$(rbenv init -)"
                    ruby --version
                    gem install bundler --no-document
                    bundle install
                '''
            }
        }

        stage('Run Fastlane') {
            steps {
                sh '''
                    export PATH="/Users/kumargaurav/.rbenv/shims:/Users/kumargaurav/.rbenv/bin:$PATH"
                    export RBENV_ROOT="/Users/kumargaurav/.rbenv"
                    eval "$(rbenv init -)"
                    bundle exec fastlane firebase_dist
                '''
            }
        }

    }

    post {
        success {
            echo '✅ Build succeeded! APK uploaded to Firebase App Distribution.'
        }
        failure {
            echo '❌ Build failed. Check the logs above.'
        }
    }
}