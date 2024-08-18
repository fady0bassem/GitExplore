package com.fadybassem.gitexplore.presentation.components.text

import android.util.Patterns
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import com.fadybassem.gitexplore.utils.Language
import com.fadybassem.gitexplore.utils.LinkType
import java.util.regex.Pattern

@Composable
fun LinkifyText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color,
    style: TextStyle,
    textAlign: TextAlign,
    maxLines: Int,
    maxCharacters: Int? = null,
    overflow: TextOverflow,
    urlClick: Boolean = true,
    addLinkStyle: Boolean = true,
    enableTextClick: Boolean = false,
    detectUrl: Boolean = true,
    language: Language = Language.ENGLISH,
    validateTermsAndConditions: Boolean = false,
    urlList: ((link: ArrayList<String>) -> Unit)? = null,
    onUrlClick: ((url: String) -> Unit)? = null,
    onClick: (() -> Unit?)? = null,
) {
    val uriHandler = LocalUriHandler.current
    val layoutResult = remember {
        mutableStateOf<TextLayoutResult?>(null)
    }

    var textString = text

    maxCharacters?.let {
        if (text.isNotBlank()) {
            val upToNCharacters: String = text.substring(0, text.length.coerceAtMost(it))
            upToNCharacters.also { string ->
                textString = if (it > text.length) string else "$string..."
            }
        }
    }

    val linksList = extractUrlsAndPhoneNumbers(textString, language, validateTermsAndConditions)

    val urls = arrayListOf<String>()
    linksList.forEach {
        urls.add(it.url)
    }
    urlList?.invoke(urls)

    val annotatedString = buildAnnotatedString {
        append(textString)

        if (detectUrl) {
            linksList.forEach { linkInfo ->
                if (addLinkStyle) {
                    val spanStyle = when (linkInfo.type) {
                        LinkType.URL -> SpanStyle(
                            color = Color.Blue, textDecoration = TextDecoration.Underline
                        )

                        LinkType.EMAIL -> SpanStyle(
                            color = Color.Blue, textDecoration = TextDecoration.Underline
                        )

                        LinkType.PHONE -> SpanStyle(
                            color = Color.Blue, textDecoration = TextDecoration.Underline
                        )

                        LinkType.INTENT -> SpanStyle(
                            color = Color.Blue, textDecoration = TextDecoration.Underline
                        )
                    }

                    addStyle(
                        style = spanStyle, start = linkInfo.start, end = linkInfo.end
                    )
                }

                addStringAnnotation(
                    tag = linkInfo.type.name,
                    annotation = linkInfo.url,
                    start = linkInfo.start,
                    end = linkInfo.end
                )
            }
        }
    }

    SelectionContainer {
        Text(
            text = annotatedString,
            modifier = modifier.pointerInput(Unit) {
                detectTapGestures { offsetPosition ->
                    layoutResult.value?.let {
                        val position = it.getOffsetForPosition(offsetPosition)
                        val result =
                            annotatedString.getStringAnnotations(position, position).firstOrNull()
                        if (result == null) {
                            if (enableTextClick) onClick?.invoke()
                        } else {
                            if (urlClick) {
                                when (result.tag) {
                                    LinkType.URL.name -> {
                                        uriHandler.openUri(result.item)
                                    }

                                    LinkType.EMAIL.name -> {
                                        uriHandler.openUri("mailto:${result.item}")
                                    }

                                    LinkType.PHONE.name -> {
                                        uriHandler.openUri("tel:${result.item}")
                                    }

                                    LinkType.INTENT.name -> {
                                        onUrlClick?.invoke(result.item)
                                    }
                                }
                            } else {
                                onUrlClick?.invoke(result.item)
                            }
                        }
                    }
                }
            },
            onTextLayout = { layoutResult.value = it },
            color = color,
            style = style,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = overflow,
        )
    }
}

private val urlPattern: Pattern = Pattern.compile(
    "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)" + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*" + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
    Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
)

private val emailPattern: Pattern = Patterns.EMAIL_ADDRESS
private val phonePattern: Pattern = Patterns.PHONE


fun extractUrlsAndPhoneNumbers(
    text: String,
    language: Language,
    validateTermsAndConditions: Boolean,
): List<LinkInfo> {
    val matcher = urlPattern.matcher(text)
    val emailMatcher = emailPattern.matcher(text)
    val phoneMatcher = phonePattern.matcher(text)

    val termsAndConditionsEnglishPattern =
        "\\bTerms\\s+and\\s+Conditions\\b".toRegex(RegexOption.IGNORE_CASE)
    val termsAndConditionsArabicPattern = "\\bالشروط والأحكام\\b".toRegex(RegexOption.IGNORE_CASE)
    val containsTermsAndMatcher = when (language) {
        Language.ENGLISH -> termsAndConditionsEnglishPattern.toPattern().matcher(text)
        Language.ARABIC -> termsAndConditionsArabicPattern.toPattern().matcher(text)
    }

    var matchStart: Int
    var matchEnd: Int
    val links = arrayListOf<LinkInfo>()

    while (matcher.find()) {
        matchStart = matcher.start(1)
        matchEnd = matcher.end()

        var url = text.substring(matchStart, matchEnd)
        if (!url.startsWith("http://") && !url.startsWith("https://")) url = "https://$url"

        links.add(LinkInfo(url, matchStart, matchEnd, LinkType.URL))
    }

    while (emailMatcher.find()) {
        matchStart = emailMatcher.start()
        matchEnd = emailMatcher.end()
        val email = text.substring(matchStart, matchEnd)
        links.add(LinkInfo(email, matchStart, matchEnd, LinkType.EMAIL))
    }

    while (phoneMatcher.find()) {
        matchStart = phoneMatcher.start()
        matchEnd = phoneMatcher.end()
        val phoneNumber = text.substring(matchStart, matchEnd)
        links.add(LinkInfo(phoneNumber, matchStart, matchEnd, LinkType.PHONE))
    }

    if (validateTermsAndConditions) {
        while (containsTermsAndMatcher.find()) {
            matchStart = containsTermsAndMatcher.start()
            matchEnd = containsTermsAndMatcher.end()
            val termsAndConditionsText = text.substring(matchStart, matchEnd)
            links.add(LinkInfo(termsAndConditionsText, matchStart, matchEnd, LinkType.INTENT))
        }
    }

    return links
}

data class LinkInfo(
    val url: String, val start: Int, val end: Int, val type: LinkType,
)