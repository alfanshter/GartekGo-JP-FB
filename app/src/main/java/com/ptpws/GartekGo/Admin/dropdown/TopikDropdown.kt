package com.ptpws.GartekGo.Admin.dropdown

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptpws.GartekGo.Commond.poppinsfamily

@Composable
fun TopikDropDown(
    label: String,
    options: List<Pair<String, String>>, // Pair<id_topik, display label>
    selectedOptionId: String?,
    onOptionSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // Cari label berdasarkan ID
    val selectedLabel = options.find { it.first == selectedOptionId }?.second
        ?.split("-")?.first()?.trim() // ambil hanya "Topik X"

    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (selectedOptionId != null) Color(0xFF2962FF) else Color.Gray
        ),
        color = Color.Transparent,
        modifier = Modifier
            .height(30.dp)
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = selectedLabel ?: label,
            fontWeight = if (selectedOptionId != null) FontWeight.Bold else FontWeight.Normal,
            fontFamily = poppinsfamily,
            fontSize = 11.sp,
            color = if (selectedOptionId != null) Color(0xFF2962FF) else Color.Gray,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        options.forEach { (id, displayLabel) ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = displayLabel,
                        fontWeight = if (id == selectedOptionId) FontWeight.Bold else FontWeight.Normal,
                        fontFamily = poppinsfamily
                    )
                },
                onClick = {
                    onOptionSelected(id)
                    expanded = false
                }
            )
        }
    }
}



