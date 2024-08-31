# Calling-Card
![213_1x_shots_so](https://github.com/user-attachments/assets/8bd7fac9-ec4f-41fd-b161-a4f8a13de180)

# Business Card App

## Overview
The Business Card App enables users to create and customize digital business cards with a user-friendly interface. Features include a carousel of card designs, slide-to-act functionality, and color customization.

## Components

### 1. StartActivity
- **Description:** Displays a carousel of business card designs that auto-scrolls.
- **Features:**
  - Carousel view with `RecyclerView` and `CarouselLayoutManager`.
  - Auto-scrolling with `Handler` and custom `SmoothScroller`.
  - Slide-to-act functionality using `SlideToActView`.

### 2. CreateActivity
- **Description:** Allows users to input their name and select a card color.
- **Features:**
  - `EditText` for user name input.
  - Color selection with predefined options and a color picker dialog.
  - Animated transitions for UI elements.
  - Navigation to `ChooseColorActivity` upon sliding completion.

### 3. ChooseColorActivity
- **Description:** Provides options to select or pick a color for the business card.
- **Features:**
  - Predefined color options and custom color picker.
  - Animation for color selection and button visibility.
  - Updates color in `DataHolder` singleton.

### 4. DataHolder
- **Description:** Singleton class for managing global data.
- **Features:**
  - Holds selected card color and user’s name.
  - Provides access to a single instance throughout the app.

### 5. CarouselAdapter
- **Description:** Adapter for displaying images in a carousel format.
- **Features:**
  - Binds image data to `RecyclerView` items.
  - Uses `EachItemBinding` for view binding.
  - Supports smooth scrolling with a custom `SmoothScroller`.

## Future Enhancements
- **Database Integration:** Store and manage business cards in a database.
- **CRUD Operations:** Create, read, update, and delete business cards.
- **Sharing & QR Code:** Share business cards and generate QR codes for easy access.
- **Improved UI/UX:** Enhance the user experience with additional features and animations.

## Contributing
I welcome contributions to improve the app! If you have suggestions, bug fixes, or new features, please fork the repository and submit a pull request.

## Usage
1. **StartActivity:** Launches the app and shows the carousel. Swipe to navigate and complete the slide to proceed.
2. **CreateActivity:** Enter your name and select a card color. Transition to color customization.
3. **ChooseColorActivity:** Select a predefined color or pick a custom color. Confirm your choice and proceed.

## Setup
1. Clone the repository.
2. Open in Android Studio.
3. Build and run the app on an Android device or emulator.

## Dependencies
- AndroidX Libraries
- Material Components
- Color picker dialog
- slidetoact
- lottiefiles
