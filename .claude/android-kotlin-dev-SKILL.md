# Android Kotlin Development Skill

## Overview
This skill provides comprehensive guidance for developing Android mobile applications using Kotlin and Java, with modern architecture patterns, best practices, and tooling.

## Core Principles

### 1. Language & Style
- **Primary Language**: Kotlin (prefer Kotlin over Java for new code)
- **Java Support**: Maintain Java interoperability when needed
- **Null Safety**: Leverage Kotlin's null safety features extensively
- **Coroutines**: Use Kotlin Coroutines for asynchronous operations (avoid RxJava for new code)
- **Extension Functions**: Use Kotlin extensions to enhance readability

### 2. Architecture Pattern
**MVVM (Model-View-ViewModel)** is the recommended architecture:

```
app/
├── data/
│   ├── local/      # Room database, SharedPreferences
│   ├── remote/     # Retrofit API services
│   ├── model/      # Data classes, entities
│   └── repository/ # Repository pattern implementations
├── domain/
│   ├── usecase/    # Business logic use cases
│   └── model/      # Domain models
├── ui/
│   ├── screens/    # Activities/Fragments organized by feature
│   │   ├── home/
│   │   ├── profile/
│   │   └── settings/
│   ├── components/ # Reusable UI components
│   ├── adapters/   # RecyclerView adapters
│   └── viewmodel/  # ViewModels
└── util/           # Utility classes, extensions
```

**Alternative Architectures**:
- **MVI** (Model-View-Intent): For complex state management
- **Clean Architecture**: For large-scale applications with clear separation of concerns

### 3. Project Structure Best Practices

#### Package Organization
```kotlin
com.yourcompany.appname/
├── data/
├── domain/
├── ui/
├── di/              # Dependency injection (Hilt/Dagger)
├── util/
└── App.kt           # Application class
```

#### Feature-Based Structure (Alternative)
```kotlin
com.yourcompany.appname/
├── feature/
│   ├── auth/
│   │   ├── data/
│   │   ├── domain/
│   │   └── ui/
│   ├── home/
│   └── profile/
├── core/            # Shared resources
└── App.kt
```

## Technology Stack

### Essential Libraries

#### Dependency Injection
```gradle
// Hilt (Recommended)
implementation "com.google.dagger:hilt-android:2.48"
kapt "com.google.dagger:hilt-compiler:2.48"

// Or Koin (Lightweight alternative)
implementation "io.insert-koin:koin-android:3.5.0"
```

#### Networking
```gradle
// Retrofit
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
implementation "com.squareup.okhttp3:logging-interceptor:4.12.0"

// Kotlin Serialization (Alternative to Gson)
implementation "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0"
implementation "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0"
```

#### Database
```gradle
// Room
implementation "androidx.room:room-runtime:2.6.0"
implementation "androidx.room:room-ktx:2.6.0"
kapt "androidx.room:room-compiler:2.6.0"
```

#### Asynchronous Operations
```gradle
// Coroutines
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3"

// Lifecycle-aware coroutines
implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.6.2"
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2"
```

#### UI Components
```gradle
// Jetpack Compose (Modern UI)
implementation platform("androidx.compose:compose-bom:2023.10.01")
implementation "androidx.compose.ui:ui"
implementation "androidx.compose.material3:material3"
implementation "androidx.compose.ui:ui-tooling-preview"
implementation "androidx.activity:activity-compose:1.8.1"

// Traditional Views
implementation "androidx.appcompat:appcompat:1.6.1"
implementation "com.google.android.material:material:1.10.0"
implementation "androidx.constraintlayout:constraintlayout:2.1.4"
```

#### Image Loading
```gradle
// Coil (Kotlin-first, recommended for Compose)
implementation "io.coil-kt:coil-compose:2.5.0"

// Glide (Traditional views)
implementation "com.github.bumptech.glide:glide:4.16.0"
kapt "com.github.bumptech.glide:compiler:4.16.0"
```

#### Navigation
```gradle
// Navigation Component
implementation "androidx.navigation:navigation-fragment-ktx:2.7.5"
implementation "androidx.navigation:navigation-ui-ktx:2.7.5"

// Compose Navigation
implementation "androidx.navigation:navigation-compose:2.7.5"
```

## Coding Standards

### 1. Naming Conventions

```kotlin
// Classes: PascalCase
class UserRepository { }
data class UserProfile { }

// Functions: camelCase
fun fetchUserData() { }
fun isValidEmail(): Boolean { }

// Properties: camelCase
val userName: String
private val isLoggedIn: Boolean

// Constants: SCREAMING_SNAKE_CASE
const val MAX_RETRY_COUNT = 3
const val API_BASE_URL = "https://api.example.com"

// Layout files: snake_case
// activity_main.xml, fragment_profile.xml, item_user_list.xml

// Resource IDs: snake_case with type prefix
// btn_submit, tv_title, rv_users, img_profile
```

### 2. File Organization

```kotlin
// Order within a class:
// 1. Companion object
// 2. Properties
// 3. Init blocks
// 4. Constructors
// 5. Override functions
// 6. Public functions
// 7. Private functions
// 8. Inner classes

class ExampleActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "ExampleActivity"
        const val EXTRA_USER_ID = "user_id"
    }
    
    private lateinit var binding: ActivityExampleBinding
    private val viewModel: ExampleViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupUI()
        observeData()
    }
    
    private fun setupUI() { }
    private fun observeData() { }
}
```

### 3. Kotlin Best Practices

#### Use Data Classes
```kotlin
// Good
data class User(
    val id: String,
    val name: String,
    val email: String
)

// Avoid for mutable entities
data class MutableUser(
    var id: String,  // Prefer immutable
    var name: String
)
```

#### Leverage Sealed Classes for State
```kotlin
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
```

#### Use Extension Functions
```kotlin
// String extensions
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

// View extensions
fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

// Context extensions
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
```

#### Scope Functions
```kotlin
// apply: configure object
val user = User().apply {
    name = "John"
    email = "john@example.com"
}

// let: null safety and transformations
val length = user?.name?.let { name ->
    name.length
}

// run: execute block and return result
val result = viewModel.run {
    fetchData()
    processData()
}

// also: additional operations
val savedUser = user.also {
    database.save(it)
    analytics.track("user_saved")
}

// with: multiple operations on object
with(binding.textView) {
    text = "Hello"
    textSize = 16f
    setTextColor(Color.BLACK)
}
```

### 4. ViewModel Pattern

```kotlin
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val uiState: StateFlow<UiState<User>> = _uiState.asStateFlow()
    
    private val _events = MutableSharedFlow<UserEvent>()
    val events: SharedFlow<UserEvent> = _events.asSharedFlow()
    
    init {
        loadUser()
    }
    
    fun loadUser() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val user = userRepository.getUser()
                _uiState.value = UiState.Success(user)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun updateUser(name: String) {
        viewModelScope.launch {
            try {
                userRepository.updateUser(name)
                _events.emit(UserEvent.UserUpdated)
            } catch (e: Exception) {
                _events.emit(UserEvent.Error(e.message ?: "Update failed"))
            }
        }
    }
}

sealed class UserEvent {
    object UserUpdated : UserEvent()
    data class Error(val message: String) : UserEvent()
}
```

### 5. Repository Pattern

```kotlin
class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val dispatchers: CoroutineDispatchers
) {
    
    suspend fun getUser(userId: String): User = withContext(dispatchers.io) {
        // Try local cache first
        userDao.getUser(userId)?.let { return@withContext it }
        
        // Fetch from network
        val response = apiService.getUser(userId)
        val user = response.toUser()
        
        // Cache locally
        userDao.insert(user)
        
        return@withContext user
    }
    
    fun observeUser(userId: String): Flow<User> {
        return userDao.observeUser(userId)
    }
}
```

### 6. Error Handling

```kotlin
// Use sealed classes for results
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

// Repository with proper error handling
suspend fun fetchData(): Result<Data> = withContext(Dispatchers.IO) {
    try {
        val response = apiService.getData()
        Result.Success(response)
    } catch (e: IOException) {
        Result.Error(NetworkException("Network error", e))
    } catch (e: HttpException) {
        Result.Error(ServerException("Server error: ${e.code()}", e))
    } catch (e: Exception) {
        Result.Error(UnknownException("Unknown error", e))
    }
}
```

## UI Development

### 1. View Binding (Traditional Views)

```kotlin
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnSubmit.setOnClickListener {
            // Handle click
        }
    }
}
```

### 2. Jetpack Compose (Modern Approach)

```kotlin
@Composable
fun UserProfileScreen(
    viewModel: UserViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Profile") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { padding ->
        when (val state = uiState) {
            is UiState.Loading -> LoadingIndicator()
            is UiState.Success -> UserContent(
                user = state.data,
                modifier = Modifier.padding(padding)
            )
            is UiState.Error -> ErrorMessage(
                message = state.message,
                onRetry = { viewModel.loadUser() }
            )
        }
    }
}
```

### 3. RecyclerView Best Practices

```kotlin
class UserAdapter : ListAdapter<User, UserAdapter.ViewHolder>(UserDiffCallback()) {
    
    var onItemClick: ((User) -> Unit)? = null
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class ViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
        
        fun bind(user: User) {
            binding.tvName.text = user.name
            binding.tvEmail.text = user.email
            // Load image with Coil/Glide
        }
    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }
    
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}
```

## Testing

### 1. Unit Tests

```kotlin
@Test
fun `when user is fetched then state updates to success`() = runTest {
    // Given
    val expectedUser = User("1", "John", "john@example.com")
    coEvery { userRepository.getUser() } returns expectedUser
    
    // When
    viewModel.loadUser()
    
    // Then
    val state = viewModel.uiState.value
    assertTrue(state is UiState.Success)
    assertEquals(expectedUser, (state as UiState.Success).data)
}
```

### 2. Required Testing Libraries

```gradle
testImplementation "junit:junit:4.13.2"
testImplementation "org.mockito.kotlin:mockito-kotlin:5.1.0"
testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3"
testImplementation "app.cash.turbine:turbine:1.0.0"

androidTestImplementation "androidx.test.ext:junit:1.1.5"
androidTestImplementation "androidx.test.espresso:espresso-core:3.5.1"
androidTestImplementation "androidx.compose.ui:ui-test-junit4"
```

## Performance Optimization

### 1. Memory Management
- Use `viewModelScope` for coroutines in ViewModels
- Cancel jobs when no longer needed
- Use `Flow` instead of `LiveData` for complex streams
- Avoid memory leaks with proper lifecycle handling

### 2. UI Performance
- Use `RecyclerView` with `DiffUtil` for efficient list updates
- Implement view recycling properly
- Use `ConstraintLayout` to reduce view hierarchy depth
- Lazy load images with Coil/Glide
- Use `ProGuard`/`R8` for code shrinking and obfuscation

### 3. Background Work
```kotlin
// WorkManager for guaranteed background execution
class DataSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    
    override suspend fun doWork(): Result {
        return try {
            // Perform sync
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
```

## Security Best Practices

1. **API Keys**: Store in `local.properties` or use encrypted storage
2. **ProGuard/R8**: Enable code obfuscation
3. **Network Security**: Use HTTPS, implement certificate pinning
4. **Data Encryption**: Use `EncryptedSharedPreferences` and `EncryptedFile`
5. **Input Validation**: Validate all user inputs
6. **Permissions**: Request only necessary permissions, handle runtime permissions properly

```kotlin
// Encrypted SharedPreferences
val sharedPreferences = EncryptedSharedPreferences.create(
    context,
    "secure_prefs",
    MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build(),
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
)
```

## Build Configuration

### Gradle Best Practices

```kotlin
// build.gradle.kts (app module)
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.yourcompany.appname"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.yourcompany.appname"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }
    
    buildFeatures {
        viewBinding = true
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
}
```

## Common Patterns

### 1. Singleton with Hilt
```kotlin
@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    
    var isLoggedIn: Boolean
        get() = prefs.getBoolean("is_logged_in", false)
        set(value) = prefs.edit().putBoolean("is_logged_in", value).apply()
}
```

### 2. Network State Monitoring
```kotlin
class NetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) {
    val isConnected: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService<ConnectivityManager>()!!
        
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true)
            }
            
            override fun onLost(network: Network) {
                trySend(false)
            }
        }
        
        connectivityManager.registerDefaultNetworkCallback(callback)
        
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()
}
```

## Documentation Standards

- Add KDoc comments for public APIs
- Document complex logic with inline comments
- Maintain README.md with setup instructions
- Keep CHANGELOG.md for version tracking
- Use TODO comments with owner: `// TODO(username): Description`

## Checklist for New Features

- [ ] Follow MVVM/Clean Architecture
- [ ] Implement proper error handling
- [ ] Add loading states
- [ ] Handle configuration changes
- [ ] Write unit tests
- [ ] Update documentation
- [ ] Check for memory leaks
- [ ] Test on different screen sizes
- [ ] Verify accessibility
- [ ] Add ProGuard rules if needed

## Resources

- [Android Developers](https://developer.android.com/)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
- [Material Design Guidelines](https://m3.material.io/)

---

**Version**: 1.0  
**Last Updated**: December 2024  
**Maintained By**: Your Team Name
