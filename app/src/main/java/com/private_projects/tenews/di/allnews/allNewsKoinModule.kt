package com.private_projects.tenews.di.allnews

import com.private_projects.tenews.data.allnews.VkAllNewsHelperImpl
import com.private_projects.tenews.domain.allnews.VkAllNewsHelper
import com.private_projects.tenews.ui.allnews.AllNewsFragment
import com.private_projects.tenews.ui.allnews.AllNewsPagerAdapter
import com.private_projects.tenews.ui.allnews.AllNewsViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val allNewsKoinModule = module {
    single<VkAllNewsHelper>(named("all_news_helper")) {
        VkAllNewsHelperImpl(
            get(named("ixbt_api")),
            get(named("rss_ferra")),
            get(named("rss_tdnews"))
        )
    }
    scope<AllNewsFragment> {
        scoped(named("all_news_adapter")) {
            AllNewsPagerAdapter()
        }
        scoped(named("all_news_view_model")) {
            AllNewsViewModel(get(named("all_news_helper")))
        }
    }
}