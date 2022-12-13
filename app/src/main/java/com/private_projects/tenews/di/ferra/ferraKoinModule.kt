package com.private_projects.tenews.di.ferra

import com.private_projects.tenews.data.ferra.VkFerraApiHelperImpl
import com.private_projects.tenews.domain.ferra.VkFerraApi
import com.private_projects.tenews.domain.ferra.VkFerraApiHelper
import com.private_projects.tenews.ui.ferra.FerraNewsFragment
import com.private_projects.tenews.ui.ferra.FerraPagerAdapter
import com.private_projects.tenews.ui.ferra.FerraViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val ferraKoinModule = module {
    single(named("ferra_api")) {
        get<Retrofit>(named("retrofit")).create(VkFerraApi::class.java)
    }
    single<VkFerraApiHelper>(named("ferra_api_helper")) {
        VkFerraApiHelperImpl(get(named("ferra_api")))
    }
    scope<FerraNewsFragment> {
        scoped(named("ferra_adapter")) {
            FerraPagerAdapter()
        }
        scoped(named("ferra_view_model")) {
            FerraViewModel(get(named("ferra_api_helper")))
        }
    }
}