package com.example.sample_jetpack_compose.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sample_jetpack_compose.model.QuestionItem
import com.example.sample_jetpack_compose.screens.questionWidgets.DottedLineWidget
import com.example.sample_jetpack_compose.screens.questionWidgets.QuestionLayoutWithAnswersWidget
import com.example.sample_jetpack_compose.screens.questionWidgets.QuestionTrackerWidget
import com.example.sample_jetpack_compose.ui.theme.AppColors
import org.koin.androidx.compose.getViewModel


@Composable
fun QuestionDisplayScreen(customViewModel: QuestionViewModel = getViewModel()) {

    val questions = customViewModel.data.value.data?.toMutableList()
    val questionIndex = remember {
        mutableStateOf(0)
    }


    if(customViewModel.data.value.loading == true) {
        CircularProgressIndicator(progress = 0.89f)
        Log.e("RESULT111", "LOADING START")

    } else {
        Log.e("RESULT111", "LOADING STOP")
        val question = try {
            questions?.get(questionIndex.value)
        } catch (e: Exception) {
            null
        }

        if (questions != null)
            QuestionDisplay(question!!,
                questionIndex = questionIndex,
                viewModel = customViewModel,
                onNextClick = {
                    questionIndex.value += 1
                },
                totalQuestions = questions.size)
    }
}


@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionViewModel,
    onNextClick: (Int) -> Unit = {},
    totalQuestions: Int
) {
    val choicesState = remember(question) {
        question.choices.toMutableList()
    }
    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer: (Int) -> Unit = remember(question) {
    {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), phase = 0f)
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(4.dp),
        color = AppColors.mDarkPurple) {
        Column(modifier = Modifier
            .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            if(questionIndex.value >= 3 ) {
                ResultBarProgress(score = questionIndex.value)
            }
            QuestionTrackerWidget(counter = questionIndex.value, outOf = totalQuestions)
            DottedLineWidget(pathEffect)
            QuestionLayoutWithAnswersWidget(
                question,
                choicesState,
                answerState,
                updateAnswer,
                correctAnswerState,
                onNextClick,
                questionIndex
            )
        }
    }
}

@Preview
@Composable
fun ResultBarProgress(score: Int = 12) {
    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075),
        Color(0xFFBE6BE5)))
    val progressFactor by remember(score) {
        mutableStateOf(score * 0.005f)
    }
    Row(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(45.dp)
        .border(
            width = 4.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    AppColors.mLightPurple,
                    AppColors.mLightPurple
                )
            ),
            shape = RoundedCornerShape(34.dp)
        )
        .clip(
            RoundedCornerShape(
                topStartPercent = 50,
                topEndPercent = 50,
                bottomEndPercent = 50,
                bottomStartPercent = 50
            )
        )
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = { /*TODO*/ },
            contentPadding = PaddingValues(1.dp),
            modifier = Modifier
                .fillMaxWidth(progressFactor)
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                containerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )
        ) {
            Text(text = (score).toString(),
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(23.dp))
                    .fillMaxHeight(0.87f)
                    .fillMaxWidth()
                    .padding(6.dp),
                color = AppColors.mOffWhite,
                textAlign = TextAlign.Center)
        }
    }
}