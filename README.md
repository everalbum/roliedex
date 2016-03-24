# Roliedex

An Android View to show independently spinning digits.

## Supported attributes (with default values)

 - **LinearLayout Attributes**
    ```RoliedexLayout``` extends ```LinearLayout```, so any of attributes of ```LinearLayout``` can be used.
     ```
     android:gravity="center_vertical"
     ```
     ```
     android:orientation="horizontal"
     ```

 - **Animation Duration**
    Time the numbers will spin before they show the final.
     ```
     app:animDuration="500"
     ```
     
 - **Text Size**
     ```
     app:textSize="16"
     ```

 - **Text Color**
     ```
     app:textColor="android:color/black"
     ```
     
 - **Number of digits before decimal**
     ```
     app:numberOfDigits="3"
     ```
      
 - **Number of decimals**
     ```
     app:numberOfDecimals="2"
     ```
     
 - **Slide In Animation**
     ```
     app:slideInAnimation="@anim/slide_in_from_bottom"
     ```
 
 - **Slide Out Animation**
     ```
     app:slideOutAnimation="@anim/slide_out_to_top"
     ```

## Limitations & Planned Improvements

 - We only support numbers. Having spinning chars would be cool.
 - The animation spinning the digits is random. It would be nice to have it synchronized. So that you can have a countdown timer, or clock, or increasing ticker.
 - We only support positive, greater than zero values.
 - We currently show all trailing decimal digits. So if the number is `0.1` and you pass `app:numberOfDecimals="2"` we will display `0.10`.