package com.droidcon.alldone.di

import android.content.Context
import com.droidcon.alldone.repository.Editor
import com.droidcon.alldone.repository.FileEditor
import com.droidcon.alldone.repository.FileRepository
import com.droidcon.alldone.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @DataSourceEditor
    @Provides
    fun provideEditor(@ApplicationContext context: Context): Editor =
        FileEditor(context = context, "to-do.json")

    @Provides
    fun provideToDoListRepository(@DataSourceEditor editor: Editor): Repository =
        FileRepository(editor)
}