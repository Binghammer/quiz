import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chadbingham.quiz.data.Question
import com.chadbingham.quiz.data.UserAnswer
import com.chadbingham.quiz.ui.theme.CricutQuizTheme
import com.chadbingham.quiz.util.PreviewQuizState

@Composable
fun MultipleChoiceQuestion(
    modifier: Modifier = Modifier,
    question: Question.MultipleChoice,
    answer: UserAnswer.MultipleChoice,
    onAnswerSelected: (UserAnswer) -> Unit,
) {

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        question.options.forEachIndexed { index, option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        val checked = !answer.indices.contains(index)
                        val indices = answer.indices.toMutableSet()
                        if (checked) {
                            indices.add(index)
                        } else {
                            indices.remove(index)
                        }
                        onAnswerSelected(UserAnswer.MultipleChoice(indices))
                    }
                    .padding(8.dp),
            ) {
                Checkbox(
                    checked = answer.indices.contains(index),
                    onCheckedChange = null
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewMultiChoice() {
    CricutQuizTheme {
        MultipleChoiceQuestion(
            modifier = Modifier,
            question = PreviewQuizState.getMultiChoiceQuestion(),
            answer = UserAnswer.MultipleChoice()
        ) {}
    }
}