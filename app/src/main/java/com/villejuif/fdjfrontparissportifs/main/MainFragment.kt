package com.villejuif.fdjfrontparissportifs.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.villejuif.fdjfrontparissportifs.R
import com.villejuif.fdjfrontparissportifs.databinding.FragmentMainBinding


class MainFragment:Fragment(), MainContract.View {

    private val TAG = MainFragment::class.java.simpleName

    private lateinit var viewDataBinding: FragmentMainBinding

    private lateinit var listTeamsAdapter: MainAdapter
    private lateinit var autoCompleteAdapter :ArrayAdapter<String>

    private lateinit var mPresenter: MainPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = MainPresenter(this)
        viewDataBinding = FragmentMainBinding.inflate(inflater, container, false).apply { mainpresenter = mPresenter }

        viewDataBinding.teamsList.layoutManager = GridLayoutManager(context, 2)

        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        setupNavigation()
        setupAutoCompletionAdapter()

    }

    override fun onStart() {
        super.onStart()
        mPresenter.searchAllTeams("French Ligue 1")
    }

    override fun onTeams() {
        setupListAdapter()
    }

    private fun setupNavigation() {
        mPresenter.clickedTeam.observe(this.viewLifecycleOwner,
            Observer {
                findNavController().navigate(
                    R.id.action_mainFragment_to_detailsFragment,
                    bundleOf("teamName" to it.strTeam, "idTeam" to it.idTeam)
                )
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

    private fun setupAutoCompletionAdapter(){
        mPresenter.leaguesNames.observe(this.viewLifecycleOwner,
            Observer {
                autoCompleteAdapter = ArrayAdapter(
                    context ?: return@Observer,
                    android.R.layout.simple_dropdown_item_1line,
                    it!!
                )

                viewDataBinding.editAutoComplete.setAdapter(autoCompleteAdapter)

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cleanUpJob()
    }
}