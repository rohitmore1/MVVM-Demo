package com.demo.mvvm.ui.features.login.viewModel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.domain.common.Resource
import com.demo.domain.feature.posts.useCase.GetLoggedInUserFromDatabaseUseCase
import com.demo.domain.feature.posts.useCase.GetPostsUseCase
import com.demo.domain.model.PostItem
import com.demo.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val context: Context,
    private val getLoggedInUserFromDatabaseUseCase: GetLoggedInUserFromDatabaseUseCase?,
    private val getPostsUseCase: GetPostsUseCase?,
) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(3000)
            getPosts()
        }
    }

    private val navigatorObserver: MutableLiveData<String?> = MutableLiveData<String?>()
    private val toastMessageObserver: MutableLiveData<String?> = MutableLiveData<String?>()
    private val loaderObserver: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    val posts: MutableState<List<PostItem>?> = mutableStateOf(null)

    fun disconnect() {
        onCleared()
    }

    private suspend fun getPosts() {

        var student = User()

        getPostsUseCase?.execute(student)?.onStart {
            emit(Resource.Loading)
        }?.collect {
            when (it) {
                is Resource.Loading -> {
                    Timber.e("Loading")
                    loaderObserver.value = true
                }

                is Resource.Success -> {
                    Timber.e("Success + ${it.data}")
                    loaderObserver.value = false
                    posts.value = it.data
                    // navigatorObserver.value = Constants.NAVIGATION_HOME
                }

                is Resource.Error -> {
                    Timber.e("Error + ${it.exception}")
                    toastMessageObserver.value = "Something went wrong!!!"
                    loaderObserver.value = false
                }

                is Resource.Empty -> {
                    Timber.e("Empty")
                    toastMessageObserver.value = "Something went wrong!!!"
                    loaderObserver.value = false
                }
            }
        }

    }

    fun showToastMessage(message: String) {
        viewModelScope.launch {
            toastMessageObserver.value = message
        }
    }

    fun getNavigatorObserver(): LiveData<String?>? {
        return navigatorObserver
    }

    fun getToastObserver(): LiveData<String?>? {
        return toastMessageObserver
    }

    fun getLoadingObserver(): LiveData<Boolean>? {
        return loaderObserver
    }

}