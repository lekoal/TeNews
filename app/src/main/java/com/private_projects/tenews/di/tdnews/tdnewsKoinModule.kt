package com.private_projects.tenews.di.tdnews

import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.ui.tdnews.TDNewsFragment
import com.private_projects.tenews.ui.tdnews.TDNewsPagerAdapter
import com.private_projects.tenews.ui.tdnews.TDNewsViewModel
import com.private_projects.tenews.utils.RssCommon
import org.koin.core.qualifier.named
import org.koin.dsl.module

val tdNewsKoinModule = module {
    single(named("rss_tdnews")) {
        RssCommon(VkHelpData.TDNEWS_RSS)
    }
    scope<TDNewsFragment> {
        scoped(named("tdnews_adapter")) {
            TDNewsPagerAdapter()
        }
        scoped(named("tdnews_view_model")) {
            TDNewsViewModel(get(named("rss_tdnews")))
        }
    }
}