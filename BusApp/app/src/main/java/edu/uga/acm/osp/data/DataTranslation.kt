package edu.uga.acm.osp.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Gite
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Interests
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Park
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SportsVolleyball
import androidx.compose.ui.graphics.vector.ImageVector
import edu.uga.acm.osp.data.baseClasses.Stop

/**
 * Retrieves the {@code ImageVector} resource that matches the provided {@code Stop.StopType}.
 *
 * @return the {@code ImageVector} resource that matches the provided {@code Stop.StopType}.
 */
fun iconFromStopType(type: Stop.StopType): ImageVector {
    when (type) {
        Stop.StopType.PARKING -> return Icons.Default.LocalParking
        Stop.StopType.HOUSING -> return Icons.Default.HomeWork
        Stop.StopType.LECTURE_HALL -> return Icons.Default.School
        Stop.StopType.LIBRARY -> return Icons.Default.LocalLibrary
        Stop.StopType.LANDMARK -> return Icons.Default.Flag
        Stop.StopType.DINING -> return Icons.Default.Restaurant
        Stop.StopType.MEDICAL -> return Icons.Outlined.LocalHospital
        Stop.StopType.SPORTS_COMPLEX -> return Icons.Outlined.SportsVolleyball
        Stop.StopType.RECREATION -> return Icons.Outlined.Interests
        Stop.StopType.NATURE -> return Icons.Outlined.Park
        Stop.StopType.OTHER -> return Icons.Outlined.Place
        Stop.StopType.SATELLITE -> return Icons.Outlined.School
        Stop.StopType.EXTERNAL -> return Icons.Outlined.Place
        Stop.StopType.GREEK_LIFE -> return Icons.Default.Gite
    }
}