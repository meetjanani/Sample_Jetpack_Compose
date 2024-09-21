package com.example.sample_jetpack_compose.screens.questionWidgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sample_jetpack_compose.model.QuestionItem
import com.example.sample_jetpack_compose.ui.theme.AppColors

@Composable
fun QuestionLayoutWithAnswersWidget(
    question: QuestionItem,
    choicesState: MutableList<String>,
    answerState: MutableState<Int?>,
    updateAnswer: (Int) -> Unit,
    correctAnswerState: MutableState<Boolean?>,
    onNextClick: (Int) -> Unit,
    questionIndex: MutableState<Int>
) {
    Column {
        Text(
            text = question.question,
            modifier = Modifier
                .padding(6.dp)
                .align(alignment = Alignment.Start)
                .fillMaxHeight(0.3f),
            fontSize = 17.sp,
            color = AppColors.mOffWhite,
            fontWeight = FontWeight.Bold,
            lineHeight = 22.sp
        )
        // choices
        choicesState.mapIndexed { index, answerText ->
            Row(
                modifier = Modifier
                    .padding(3.dp)
                    .fillMaxWidth()
                    .height(45.dp)
                    .border(
                        width = 4.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(AppColors.mOffDarkPurple, AppColors.mOffDarkPurple)
                        ),
                        shape = RoundedCornerShape(15.dp)
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (answerState.value == index), onClick = {
                        updateAnswer(index)
                    },
                    modifier = Modifier.padding(start = 16.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = if (correctAnswerState.value == true && index == answerState.value) {
                            Color.Green.copy(alpha = 0.6f)
                        } else {
                            Color.Red.copy(alpha = 0.6f)
                        }
                    )
                )
                val annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Light,
                            color = if (correctAnswerState.value == true && index == answerState.value) {
                                Color.Green
                            } else if (correctAnswerState.value == false && index == answerState.value) {
                                Color.Red
                            } else {
                                AppColors.mOffWhite
                            },
                            fontSize = 17.sp
                        )
                    ) {
                        append(answerText)
                    }
                }
                Text(text = annotatedString, modifier = Modifier.padding(6.dp))
            }
        }

        // TODO: Button
        Button(
            onClick = {
                onNextClick(questionIndex.value)
            },
            modifier = Modifier
                .padding(3.dp)
                .align(alignment = Alignment.CenterHorizontally),
            shape = RoundedCornerShape(34.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColors.mLightBlue,
            )
        ) {
            Text(
                text = "Next", modifier = Modifier.padding(4.dp),
                color = AppColors.mOffWhite,
                fontSize = 17.sp
            )
        }

    }
}