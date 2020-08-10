package com.example.projectmobile

const val HUNDRED_PERCENT = 100.00

class TipCalculator {

    fun calculatePerPersonTotal(totalBill: Double, numberOfPeople: Int, tipPercent: Int, taxAmount: Double, tax: Boolean, split: Boolean) : Double
    {
        var totalExpense: Double = if(tax)
        {
            (((HUNDRED_PERCENT + tipPercent) / HUNDRED_PERCENT) * (verifyTip(totalBill) + verifyTax(taxAmount)))
        }
        else
        {
            (((HUNDRED_PERCENT + tipPercent) / HUNDRED_PERCENT) * (verifyTip(totalBill))) + verifyTax(taxAmount)
        }

        if (split)
        {
            return "%.2f".format(totalExpense / numberOfPeople).toDouble()
        }

        return "%.2f".format(totalExpense).toDouble()
    }

    private fun verifyTip(tip: Double) : Double
    {
        if (tip < 0.0) {
            return 0.0
        }
        return tip
    }

    private fun verifyTax(tax: Double) : Double
    {
        if (tax < 0.0) {
            return 0.0
        }
        return tax
    }
}
