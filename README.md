# MovLancer

Dear Sir/Madam,

This project is built on top of AAC MVVM model, it fetches data from TMDB API and stores in local SQLlite db, and makes use of LiveData / MutableLiveData to notify observers for the API return result/ search result.

Currently this app can:
1. Load data from TMDB API
2. Scroll to load more data
3. Swipe to reload list
4. Search title from existing list

This app uses Retrofit library to fetch data, and Picasso to download and display images.