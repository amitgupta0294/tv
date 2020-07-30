package com.hash.cotinum.repo

class HomeRepo {

    companion object {
        private var repository: HomeRepo = HomeRepo()

        fun getHomeRepoInstance(): HomeRepo {
            return repository
        }
    }
}