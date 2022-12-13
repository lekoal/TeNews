package com.private_projects.tenews.di.tdnews

import com.private_projects.tenews.data.tdnews.VkTdnewsApiHelperImpl
import com.private_projects.tenews.domain.tdnews.VkTdnewsApi
import com.private_projects.tenews.domain.tdnews.VkTdnewsApiHelper
import com.private_projects.tenews.ui.tdnews.TDNewsFragment
import com.private_projects.tenews.ui.tdnews.TDNewsPagerAdapter
import com.private_projects.tenews.ui.tdnews.TDNewsViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val tdNewsKoinModule = module {
    single(named("tdnews_api")) {
        get<Retrofit>(named("retrofit")).create(VkTdnewsApi::class.java)
    }
    single<VkTdnewsApiHelper>(named("tdnews_api_helper")) {
        VkTdnewsApiHelperImpl(get(named("tdnews_api")))
    }
    scope<TDNewsFragment> {
        scoped(named("tdnews_adapter")) {
            TDNewsPagerAdapter()
        }
        scoped(named("tdnews_view_model")) {
            TDNewsViewModel(get(named("tdnews_api_helper")))
        }
    }
}