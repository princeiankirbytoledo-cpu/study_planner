package ph.edu.comteq.studyplaner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ph.edu.comteq.studyplaner.ui.navigation.AppNavigation
import ph.edu.comteq.studyplaner.ui.theme.StudyPlanerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyPlanerTheme {
                AppNavigation()
            }
        }
    }
}
