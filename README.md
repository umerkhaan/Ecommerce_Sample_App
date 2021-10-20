# Ecommerce Sample App

It's an Ecommerce application built on MVVM architecture using [FakeStoreAPI](http://fakestoreapi.com) with:
  * Products Listing
  * Product Details Page
  * Cart Functionality

**Application Uses:**
  * Hilt for DI
  * Live Data
  * Navigation Components
  * Data Binding
  * Room Database
  * Kotlin Coroutines Adapter for Retrofit2
  * Kotlin Corotines
  * Shimmer for Loading Animations
  * Glide for Lazy Loading
  
**Challenges while building:**

  Main challenge while building the application was to handle the cart items consistently across the application
  I stored the cart data in room database and used LiveData to consistently reflect the cart items count and products
  across the application.
  
**How to run the Application?**

  Clone the application and just open the project in Android Studio no API KEY or ACCESS TOKEN required as it's using
  a temprary [FakeStoreAPI](http://fakestoreapi.com).
  
