package com.fredy.mytest

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainViewModel:ViewModel(){

    var state by mutableStateOf(MainScreenState())
        private set

    private val uriPathFinder = UriPathFinder()

    fun onFilePathsListChange(list:List<Uri>,context: Context){
        val updatedList = state.filePaths.toMutableList()
        val paths = changeUriToPath(list,context)
        viewModelScope.launch {
            updatedList += paths
            state = state.copy(
                filePaths = updatedList
            )
        }
    }

    private fun changeUriToPath(uris:List<Uri>,context: Context):List<String>{
        val pathsList:MutableList<String> = mutableListOf()
        uris.forEach {
           val path =  uriPathFinder.getPath(context,it)
            pathsList.add(path!!)
        }
        return pathsList
    }


}