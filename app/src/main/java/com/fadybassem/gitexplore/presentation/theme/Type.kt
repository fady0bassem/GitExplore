package com.fadybassem.gitexplore.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fadybassem.gitexplore.R

val arabicFontFamily = FontFamily(
    Font(R.font.sans_english_extra_light, FontWeight.ExtraLight),
    Font(R.font.sans_arabic_light, FontWeight.Light),
    Font(R.font.sans_arabic_regular, FontWeight.Normal),
    Font(R.font.sans_arabic_medium, FontWeight.Medium),
    Font(R.font.sans_arabic_semi_bold, FontWeight.SemiBold),
    Font(R.font.sans_arabic_bold, FontWeight.Bold)
)

val englishFontFamily = FontFamily(
    Font(R.font.sans_english_extra_light, FontWeight.ExtraLight),
    Font(R.font.sans_english_light, FontWeight.Light),
    Font(R.font.sans_english_regular, FontWeight.Normal),
    Font(R.font.sans_english_medium, FontWeight.Medium),
    Font(R.font.sans_english_semi_bold, FontWeight.SemiBold),
    Font(R.font.sans_english_bold, FontWeight.Bold)
)

val ArabicTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 30.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 28.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 26.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 23.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 22.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 21.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 19.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 17.sp
    ),
    bodySmall = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 15.sp
    ),
    labelMedium = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = arabicFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 13.sp
    )
)

val EnglishTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 30.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 28.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W700,
        fontSize = 26.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 23.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = 22.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 21.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 19.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 17.sp
    ),
    bodySmall = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 15.sp
    ),
    labelMedium = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = englishFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 13.sp
    )
)