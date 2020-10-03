package com.villejuif.fdjfrontparissportifs.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private lateinit var listAdapter: MainAdapter

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
                ) })
    }

    private fun setupListAdapter() {
        val mainPresenter = viewDataBinding.mainpresenter
        if (mainPresenter != null) {
            listAdapter = MainAdapter(mainPresenter)
            viewDataBinding.teamsList.adapter = listAdapter
        } else {
            Log.d(TAG,"MainPresenter not initialized when attempting to set up adapter.")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.cleanUpJob()
    }
}