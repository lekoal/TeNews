package com.private_projects.tenews.domain

import androidx.fragment.app.Fragment

abstract class RefreshFragmentContract : Fragment() {
    abstract fun refresh()
}