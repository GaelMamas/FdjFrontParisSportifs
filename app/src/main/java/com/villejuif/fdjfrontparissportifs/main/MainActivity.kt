package com.villejuif.fdjfrontparissportifs.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.villejuif.fdjfrontparissportifs.R
import com.villejuif.fdjfrontparissportifs.databinding.ActivityMainBinding
import com.villejuif.fdjfrontparissportifs.details.DetailsActivity


class MainActivity : AppCompatActivity(), MainContract.View {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var viewDataBinding: ActivityMainBinding

    private lateinit var listTeamsAdapter: MainAdapter
    private lateinit var autoCompleteAdapter: ArrayAdapter<String>

    private lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter = MainPresenter(this)

        viewDataBinding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)

        setSupportActionBar(viewDataBinding.toolbar)

        viewDataBinding.teamsList.layoutManager = GridLayoutManager(this, 2)

        mPresenter.teams.observe(this,
            Observer {
                viewDataBinding.mainpresenter = mPresenter
                setupListAdapter()
            })

        setupPermissions()

        setupNavigation()
        setupAutoCompletionAdapter()
        setupAutoCompletionListener()
        clearEditText()
    }

    override fun onTeams() {
        hideKeyboard()
    }

    fun clearEditText(){
        viewDataBinding.imageCancel.setOnClickListener {
            viewDataBinding.editAutoComplete.text = null
        }
    }

    private fun setupNavigation() {
        mPresenter.clickedTeam.observe(this,
            Observer {
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.EXTRA_STRING_1, it.strTeam)
                intent.putExtra(DetailsActivity.EXTRA_STRING_2, it.idTeam)
                startActivity(intent)
            })
    }

    private fun setupListAdapter() {
        val mainPresenter = viewDataBinding.mainpresenter
        if (mainPresenter != null) {
            listTeamsAdapter = MainAdapter(mainPresenter)
            viewDataBinding.teamsList.adapter = listTeamsAdapter

        } else {
            Log.d(TAG, "MainPresenter not initialized when attempting to set up adapter.")
        }
    }

    private fun setupAutoCompletionAdapter() {
        mPresenter.leaguesNames.observe(this,
            Observer {
                autoCompleteAdapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_dropdown_item_1line,
                    it!!
                )

                viewDataBinding.editAutoComplete.setAdapter(autoCompleteAdapter)

            })
    }

    private fun setupAutoCompletionListener() {
        viewDataBinding.editAutoComplete.setOnItemClickListener { parent, view, position, _ ->
            mPresenter.searchAllTeams(parent.getItemAtPosition(position).toString())
        }

        viewDataBinding.editAutoComplete.setOnEditorActionListener { view, actionId, keyEvent ->
            return@setOnEditorActionListener if ((actionId == EditorInfo.IME_ACTION_SEARCH)) {
                mPresenter.searchAllTeams(view.text.toString())
                Log.i(TAG, "Action Search for ${view.text}")

                viewDataBinding.editAutoComplete.dismissDropDown()

                true
            } else false
        }
    }

    private fun hideKeyboard() {
        val inn: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inn.hideSoftInputFromWindow(viewDataBinding.root.applicationWindowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cleanUpJob()
    }

    private fun setupPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.INTERNET
                ), 1
            )
        }
    }

}