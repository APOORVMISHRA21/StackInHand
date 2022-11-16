# StackInHand
 An android client for rendering stack overflow questions.

  - [Kotlin](https://kotlinlang.org)
  - [Constraint Layout](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout)
  - [MVVM Architecture](https://developer.android.com/topic/architecture?gclid=Cj0KCQiAsdKbBhDHARIsANJ6-jeVf_Xs8UmFld-xfzvpOirvpaZjY2xK15uC2h0Tq-jJXrmOp0b4smMaAhoeEALw_wcB&gclsrc=aw.ds)
  - [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
  - [Retrofit](https://square.github.io/retrofit/)
  - [Glide](https://github.com/bumptech/glide)
  - [Dagger-Hilt](https://dagger.dev/hilt/)

 The application comes with the Splash Activity as the entry point for application, transitioning from which user enters the Main Activity, which server the following features.

  - A list of questions fetched through a [url](https://api.stackexchange.com/2.2/questions?key=ZiXCZbWaOwnDgpVT9Hx8IA%28%28&order=desc&sort=activity&site=stackoverflow) from stackoverflow rendered into app using [RecyclerView](https://developer.android.com/develop/ui/views/layout/recyclerview) .
  - A Search Bar to implement search feature based on question title and question author using Android [Search View](https://developer.android.com/reference/android/widget/SearchView) .
  - A Filter Button to implement filtering based on tags on question objects implemented in Bottom Sheets. 

Go To [master](https://github.com/APOORVMISHRA21/StackInHand/tree/master) Branch to fetch the latest stable application.
