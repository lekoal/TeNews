package com.private_projects.tenews.di.details

import com.private_projects.tenews.ui.details.DetailsFragment
import com.private_projects.tenews.ui.details.DetailsViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val detailsKoinModule = module {
    scope<DetailsFragment> {
        scoped(named("details_view_model")) {
            DetailsViewModel()
        }
    }
}