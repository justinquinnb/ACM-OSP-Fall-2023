package edu.uga.acm.osp.components

import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.outlined.Abc
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.uga.acm.osp.data.baseClasses.Bus
import edu.uga.acm.osp.data.baseClasses.Route
import edu.uga.acm.osp.data.baseClasses.Stop
import edu.uga.acm.osp.data.iconFromStopType
import edu.uga.acm.osp.data.sources.ExampleGenerator
import edu.uga.acm.osp.data.sources.StaticExampleData
import edu.uga.acm.osp.ui.theme.BusAppTheme

@Preview
@Composable
fun BusOverviewPreview() {
    val route: Route = StaticExampleData.getRoute()
    BusOverview(route)
}

//@Preview
@Composable
private fun StopTopPreview() {

}

//@Preview
@Composable
private fun StopBottomPreview() {

}

/**
 *  Subway-style map of buses and their position along the provided route.
 */
@Composable
fun BusOverview(route: Route) {
    val busOverview: HashMap<Long, Array<Bus>> = route.busOverview()

    // Isolate the "inner" stops (non-first/last stops)
    val innerStops: HashMap<Long, Array<Bus>> = HashMap<Long, Array<Bus>>()
    var topStopEntry: Map.Entry<Long, Array<Bus>>? = null
    var bottomStopEntry: Map.Entry<Long, Array<Bus>>? = null

    var i: Int = 0
    for (item: MutableMap.MutableEntry<Long, Array<Bus>> in busOverview) {
        if (i == 0) {
            topStopEntry = item
        } else if (i != 0 && i != (busOverview.size - 1)) {
            innerStops[item.key] = item.value
        } else if (i == (busOverview.size - 1)) {
            bottomStopEntry = item
        }
        i++
    }

    val routeColor: Color = Color(route.displayColorAsLong())

    // Now that the info is correctly separated, render it
    BasicContainer(
        containerHeader = "Live Overview",
        context = {
            ContextInfo(
                contextText = route.totalUniqueBuses().toString() + " active",
                contextIcon = Icons.Default.DirectionsBus,
                contextDesc = "Number of Buses")
        }) {
        Column {
            TopStop(routeColor, topStopEntry)

            // Inner stops
            innerStops.forEach {innerStopEntry ->
                InnerStop(routeColor, innerStopEntry)
            }

            BottomStop(routeColor, bottomStopEntry)
        }
    }
}

/**
 * The topmost stop item to render.
 */
@Composable
private fun TopStop(color: Color, pairing: Map.Entry<Long, Array<Bus>>?) {
    var text: String = "Top: Nonexistent"
    if (pairing != null) {
        text = "Top: " + StaticExampleData.getStop(pairing.key).name
    }
    Column() {
        Box(Modifier.clip(CircleShape).size(8.dp).background(color)){}
        Spacer(Modifier.size(10.dp))
        StopSpot(color, pairing)
        Text(text)
    }
}

/**
 * The bottommost stop item to render.
 */
@Composable
private fun BottomStop(color: Color, pairing: Map.Entry<Long, Array<Bus>>?) {
    var text: String = "Bottom: Nonexistent"
    if (pairing != null) {
        text = "Bottom: " + StaticExampleData.getStop(pairing.key).name
    }
    Text(text)
}

/**
 * Inner stops.
 */
@Composable
private fun InnerStop(color: Color, pairing: Map.Entry<Long, Array<Bus>>?) {
    var text: String = "Inner: Nonexistent"
    if (pairing != null) {
        text = "Inner: " + StaticExampleData.getStop(pairing.key).name
    }
    Text(text)
}

/**
 * A "stop" spot to display on the subway-style map.
 */
@Composable
private fun StopSpot(color: Color, pairing: Map.Entry<Long, Array<Bus>>?) {
    Box(
        Modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(width = 8.dp, color = color)

    ) {
        Box(
            Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(BusAppTheme.colors.container)
        ) {
            if (pairing != null) {
                Icon(
                    imageVector = (iconFromStopType(StaticExampleData.getStop(pairing.key).type)),
                    contentDescription = Stop.stringFromType(StaticExampleData.getStop(pairing.key).type),
                    tint = color)
            }
        }
    }
}

/**
 * The path to connect "stop spots" on the subway-style "bus overview" map.
 */
@Composable
private fun Path(color: Color, buses: Array<Bus>?) {

}