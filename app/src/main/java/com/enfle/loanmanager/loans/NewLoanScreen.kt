package com.enfle.loanmanager.loans

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.enfle.loanmanager.beans.LoanType

@Composable
fun NewLoanScreen(
    viewModel: NewLoanViewModel = viewModel()
) {
    val loanType = viewModel.loanType.collectAsState().value
    Scaffold(backgroundColor = Color.White,
        content = {
            NewLoan(loanType = loanType, viewModel)
        })
}

@Composable
private fun NewLoan(loanType: LoanType, viewModel: NewLoanViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        val borrowerPhoneNumber by viewModel.phoneNumber.collectAsState("")
        Text(
            text = "Borrower's Phone number",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 10.dp),
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = borrowerPhoneNumber,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = { viewModel.onPhoneNumberChange(it) },
        )

        Spacer(modifier = Modifier.height(20.dp))

        val borrowerName by viewModel.name.collectAsState("")
        Text(
            text = "Borrower Name",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 10.dp),
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = borrowerName,
            onValueChange = { },
        )

        Spacer(modifier = Modifier.height(20.dp))

        val description by viewModel.description.collectAsState("")
        Text(
            text = "Loan Description",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 10.dp),
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            placeholder = { Text(text = "Enter loan description") },
            onValueChange = { },
        )

        Spacer(modifier = Modifier.height(20.dp))

        val loanAmount by viewModel.loanAmount.collectAsState("")
        Text(
            text = "Loan Amount",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 10.dp),
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = loanAmount,
            keyboardOptions  = KeyboardOptions.Default,
            onValueChange = { },
        )

        Spacer(modifier = Modifier.height(20.dp))
        LoanTypeSelector()

        if (loanType == LoanType.EMI) {
            Spacer(modifier = Modifier.height(20.dp))
            InterestTypeSelector()
        }

        Button(
            onClick = {
            },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 24.dp)
        ) {
            Text(text = "Add Client")
        }
    }
}

@Composable
fun LoanTypeSelector() {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(0) }

    val titles = listOf("TERM", "EMI")
    Column {
        Text(
            text = "Loan Type",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 10.dp),
        )
        TabRow(selectedTabIndex = selectedTab) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { setSelectedTab(index) },
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(5.dp))
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InterestTypeSelector() {
    val (selectedTab, setSelectedTab) = remember { mutableStateOf(0) }
    val titles = listOf("NONE", "SIMPLE", "COMPOUND")
    Column {
        Text(
            text = "Interest Type",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .padding(bottom = 10.dp),
        )
        TabRow(
            selectedTabIndex = selectedTab,
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { setSelectedTab(index) },
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(5.dp))
                    ) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmiPeriodSelector(term: LoanType) {
    Row(
        modifier = Modifier
            .padding(10.dp)
    ) {

        if (term == LoanType.EMI) {
            OutlinedButton(
                onClick = {
                },
                colors = ButtonDefaults.outlinedButtonColors(),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth(0.50f)
            ) {
                Text(text = "Start Date")
            }
        }
        Button(
            onClick = {
            },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.outlinedButtonColors(),
            modifier = Modifier.fillMaxWidth(0.50f)
        ) {
            Text(text = "Start Date", modifier = Modifier.fillMaxWidth())
        }
    }
}

@Preview
@Composable
fun PreviewEmiPeriodSelector() {
    Surface(modifier = Modifier.fillMaxWidth()) {
        EmiPeriodSelector(LoanType.EMI)
    }
}

@Preview
@Composable
fun PreviewNewLoanTerm() {
    Surface {
        NewLoan(loanType = LoanType.TERM, viewModel = NewLoanViewModel())
    }
}

@Preview
@Composable
fun PreviewNewLoanEMI() {
    Surface {
        NewLoan(loanType = LoanType.EMI, viewModel = NewLoanViewModel())
    }
}