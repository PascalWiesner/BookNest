package de.syntax_institut.androidabschlussprojekt.di

import android.content.Context
import androidx.room.Room
import de.syntax_institut.androidabschlussprojekt.library.data.repository.LibraryBookRepositoryImpl
import de.syntax_institut.androidabschlussprojekt.library.domain.repository.LibraryBookRepositoryInterface
import de.syntax_institut.androidabschlussprojekt.library.domain.usecase.GetLibraryBooksUseCase
import de.syntax_institut.androidabschlussprojekt.login.viewmodel.InfoViewModel
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.BookNotesLocalDao
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.MyLibraryLocalBookDao
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.daos.WatchlistLocalBookDao
import de.syntax_institut.androidabschlussprojekt.core.data.database.local.database.AppDatabase
import de.syntax_institut.androidabschlussprojekt.core.data.database.remote.api.BookAPI
import de.syntax_institut.androidabschlussprojekt.core.data.database.remote.api.RemoteBookAPIService
import de.syntax_institut.androidabschlussprojekt.core.data.repository.Impl.BookNoteRepositoryImpl
import de.syntax_institut.androidabschlussprojekt.core.data.repository.Impl.LibraryBookWithNoteRepositoryImpl
import de.syntax_institut.androidabschlussprojekt.core.data.repository.Impl.SaveLocalBookRepositoryImpl
import de.syntax_institut.androidabschlussprojekt.core.data.repository.Impl.WatchListSaveRemoteBookRepositoryImpl
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.BookNoteRepositoryInterface
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.LibraryBookWithNoteRepository
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.SaveLocalBookRepository
import de.syntax_institut.androidabschlussprojekt.core.data.repository.interfaces.WatchListSaveRemoteBookRepository
import de.syntax_institut.androidabschlussprojekt.faq.data.repository.FAQRepositoryImpl
import de.syntax_institut.androidabschlussprojekt.faq.domain.repository.FAQRepository
import de.syntax_institut.androidabschlussprojekt.faq.domain.usecase.GetFAQsUseCase
import de.syntax_institut.androidabschlussprojekt.faq.viewmodel.FAQViewModel
import de.syntax_institut.androidabschlussprojekt.library.domain.usecase.DeleteBookNoteinLibraryUseCase
import de.syntax_institut.androidabschlussprojekt.library.domain.usecase.DeleteLibraryBookWithNoteUseCase
import de.syntax_institut.androidabschlussprojekt.library.domain.usecase.GetLibraryBookWithNoteUseCase
import de.syntax_institut.androidabschlussprojekt.library.domain.usecase.SaveLibraryBookNoteUseCase
import de.syntax_institut.androidabschlussprojekt.library.viewmodel.LibraryScreenViewModel
import de.syntax_institut.androidabschlussprojekt.login.data.repositories.UserRepository
import de.syntax_institut.androidabschlussprojekt.login.data.service.AuthenticationService
import de.syntax_institut.androidabschlussprojekt.login.domain.usecases.DeleteAccountUseCase
import de.syntax_institut.androidabschlussprojekt.login.domain.usecases.RegisterWithEmailUseCase
import de.syntax_institut.androidabschlussprojekt.login.domain.usecases.SignInWithEmailUseCase
import de.syntax_institut.androidabschlussprojekt.login.domain.usecases.SignInWithGoogleUseCase
import de.syntax_institut.androidabschlussprojekt.login.domain.usecases.SignOutUseCase
import de.syntax_institut.androidabschlussprojekt.login.viewmodel.AuthenticationViewModel
import de.syntax_institut.androidabschlussprojekt.remotebooks.data.repository.BookRepositoryImpl
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.repository.RemoteBooksRepositoryInterface
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase.GetRemoteBooksUseCase
import de.syntax_institut.androidabschlussprojekt.remotebooks.viewmodel.BookViewModel
import de.syntax_institut.androidabschlussprojekt.remotebooks.viewmodel.BrowseViewModel
import de.syntax_institut.androidabschlussprojekt.remotebooks.viewmodel.DetailScreenViewModel
import de.syntax_institut.androidabschlussprojekt.scanner.data.repository.ScannerRepositoryImpl
import de.syntax_institut.androidabschlussprojekt.scanner.data.service.ScannerService
import de.syntax_institut.androidabschlussprojekt.scanner.domain.repository.ScannerRepositoryInterface
import de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase.AddBookToLibraryUseCase
import de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase.ScanBookByIsbnUseCase
import de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase.SearchBookByISBNUseCase
import de.syntax_institut.androidabschlussprojekt.scanner.viewmodel.ISBNManuallyViewModel
import de.syntax_institut.androidabschlussprojekt.scanner.viewmodel.ScannerViewModel
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.repository.WatchListBookInterface
import de.syntax_institut.androidabschlussprojekt.watchlist.viewmodel.WatchListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import de.syntax_institut.androidabschlussprojekt.watchlist.data.repository.WatchListRepositoryImpl
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase.AddRemoteBookToWatchlistUseCase
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase.ObserveWatchListBookUseCase
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.usecase.RemoveRemoteBookFromWatchlistUseCase
import de.syntax_institut.androidabschlussprojekt.scanner.domain.usecase.SaveNoteUseCase
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.usecase.DeleteWatchListBookUseCase
import de.syntax_institut.androidabschlussprojekt.watchlist.domain.usecase.GetWatchListBooksUseCase

val appModule = module {
    // Services
    singleOf(::AuthenticationService)
    singleOf(::ScannerService)

    // Room & DAOs
    single<AppDatabase> {
        Room.databaseBuilder(
            get<Context>(),
            AppDatabase::class.java,
            "local_books_db"
        ).build()
    }

    single<MyLibraryLocalBookDao> {
        get<AppDatabase>().myLibrarylocalBookDao()
    }

    single<WatchlistLocalBookDao> {
        get<AppDatabase>().watchlistLocalBookDao()
    }

    single<BookNotesLocalDao> {
        get<AppDatabase>().localBookNoteDao()
    }


    // Remote API
    single<RemoteBookAPIService> {
        BookAPI.retrofitService
    }

    // Repositories
    single<LibraryBookRepositoryInterface> {
        LibraryBookRepositoryImpl(get())
    }

    single<BookNoteRepositoryInterface> {
        BookNoteRepositoryImpl(get())
    }

    single<WatchListBookInterface> {
        WatchListRepositoryImpl(get())
    }

    single<SaveLocalBookRepository> {
        SaveLocalBookRepositoryImpl(get())
    }

    single<WatchListSaveRemoteBookRepository> {
        WatchListSaveRemoteBookRepositoryImpl(get())
    }

    single<LibraryBookWithNoteRepository> {
        LibraryBookWithNoteRepositoryImpl(
            get<MyLibraryLocalBookDao>(),
            get<BookNoteRepositoryInterface>()
        )
    }

    single<ScannerRepositoryInterface> {
        ScannerRepositoryImpl(
            remoteBookApiService = get(),
            localBookSaver = get()
        )
    }

    single<RemoteBooksRepositoryInterface> {
        BookRepositoryImpl(get())
    }

    single<FAQRepository>{
        FAQRepositoryImpl()
    }

    singleOf(::UserRepository)

    // UseCases
    singleOf(::SignInWithGoogleUseCase)
    singleOf(::SignOutUseCase)
    singleOf(::SignInWithEmailUseCase)
    singleOf(::RegisterWithEmailUseCase)
    singleOf(::ScanBookByIsbnUseCase)
    singleOf(::AddBookToLibraryUseCase)
    singleOf(::SearchBookByISBNUseCase)
    singleOf(::GetRemoteBooksUseCase)
    singleOf(::GetLibraryBooksUseCase)
    singleOf(::DeleteBookNoteinLibraryUseCase)
    singleOf(::GetWatchListBooksUseCase)
    singleOf(::DeleteWatchListBookUseCase)
    singleOf(::AddRemoteBookToWatchlistUseCase)
    singleOf(::RemoveRemoteBookFromWatchlistUseCase)
    singleOf(::ObserveWatchListBookUseCase)
    singleOf(::DeleteLibraryBookWithNoteUseCase)
    singleOf(::GetLibraryBookWithNoteUseCase)
    singleOf(::SaveLibraryBookNoteUseCase)
    singleOf(::SaveNoteUseCase)
    singleOf(::DeleteAccountUseCase)
    singleOf(::GetFAQsUseCase)

    // ViewModels
    viewModelOf(::AuthenticationViewModel)
    viewModelOf(::InfoViewModel)
    viewModelOf(::BookViewModel)
    viewModelOf(::DetailScreenViewModel)
    viewModelOf(::ScannerViewModel)
    viewModelOf(::ISBNManuallyViewModel)
    viewModelOf(::LibraryScreenViewModel)
    viewModelOf(::WatchListViewModel)
    viewModelOf(::BrowseViewModel)
    viewModelOf(::FAQViewModel)
}