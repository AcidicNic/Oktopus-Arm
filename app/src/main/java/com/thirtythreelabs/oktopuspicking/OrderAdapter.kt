package com.thirtythreelabs.oktopuspicking

import android.content.Context
import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.thirtythreelabs.oktopuspicking.utils.GlobalVars
import com.thirtythreelabs.oktopuspicking.utils.Header
import org.ocpsoft.prettytime.Duration
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderAdapter(context: Context, private val resource: Int, private val headers: List<Header>) : ArrayAdapter<Header>(context, resource, headers) {

    private lateinit var orderNumTxt: TextView
    private lateinit var refNumTxt: TextView
    private lateinit var orderDateTxt: TextView
    private lateinit var billingDateTxt: TextView
    private lateinit var vendorNameTxt: TextView
    private lateinit var customerNameTxt: TextView
    private lateinit var orderTypeTxt: TextView
    private lateinit var quantityTxt: TextView
    private lateinit var totalItemsTxt: TextView
    private lateinit var totalDiffToPickTxt: TextView
    private lateinit var totalPickedTxt: TextView
    private lateinit var totalPausedTxt: TextView
    private lateinit var orderNotesTxt: TextView
    private lateinit var takeOrderBtn: Button
//    private lateinit var imgView: ImageView

    var orderTaken: Boolean = false

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)

        orderNumTxt = view.findViewById(R.id.order_num)
        refNumTxt = view.findViewById(R.id.ref_num)
        orderDateTxt = view.findViewById(R.id.order_date)
        billingDateTxt = view.findViewById(R.id.billing_date)
        vendorNameTxt = view.findViewById(R.id.vendor_name)
        customerNameTxt = view.findViewById(R.id.customer_name)
        orderTypeTxt = view.findViewById(R.id.order_type)
        quantityTxt = view.findViewById(R.id.quantity)
        totalItemsTxt = view.findViewById(R.id.total_items)
        totalDiffToPickTxt = view.findViewById(R.id.items_diff_to_pick)
        totalPickedTxt = view.findViewById(R.id.items_picked)
        totalPausedTxt = view.findViewById(R.id.items_paused)
        orderNotesTxt = view.findViewById(R.id.order_notes)
        takeOrderBtn = view.findViewById(R.id.take_order_button)
//        imgView = view.findViewById(R.id.background_img)

        val order = headers[position]

        orderNumTxt.text = order.HeaderId
        refNumTxt.text = "(Ref: ${order.HeaderRefId})"
        orderDateTxt.text = "${getDuration(order.HeaderDate)}"
//        orderDateTxt.text = "${getDuration(order.HeaderDate)} (${order.PrettyHeaderDate})"

        billingDateTxt.text = Html.fromHtml(
            "${context.getString(R.string.billing_date)}: <b>${order.PrettyHeaderDate}</b>",
            Html.FROM_HTML_MODE_LEGACY)

        vendorNameTxt.text = Html.fromHtml(
            "${context.getString(R.string.vendor)}: <b>${order.SalesmanId} - ${order.SalesmanName}</b>",
                Html.FROM_HTML_MODE_LEGACY)

        customerNameTxt.text = Html.fromHtml(
                    "${context.getString(R.string.client)}: <b>${order.CustomerId} - ${order.CustomerName}",
            Html.FROM_HTML_MODE_LEGACY)

        orderTypeTxt.text = Html.fromHtml(
            "${context.getString(R.string.order_type)}: <b>${order.DeliveryTypeDescription}",
            Html.FROM_HTML_MODE_LEGACY)

        quantityTxt.text = "${order.TotalLines - order.TotalLinesPicked}"
        totalItemsTxt.text = "${context.getString(R.string.of)} ${order.TotalLines}"

        if (order.HeaderItemsDiffToPick > 1) {
            totalDiffToPickTxt.text = "${order.HeaderItemsDiffToPick} ${context.getString(R.string.tricky_plural)}"
            totalDiffToPickTxt.visibility = View.VISIBLE
        } else if (order.HeaderItemsDiffToPick == 1) {
            totalDiffToPickTxt.text = "${order.HeaderItemsDiffToPick} ${context.getString(R.string.tricky_singular)}"
            totalDiffToPickTxt.visibility = View.VISIBLE
        } else {
            totalDiffToPickTxt.visibility = View.GONE
        }

        if (order.TotalLinesPicked > 1) {
            totalPickedTxt.text = "${order.TotalLinesPicked} ${context.getString(R.string.picked_plural)}"
            totalPickedTxt.visibility = View.VISIBLE
        } else if (order.TotalLinesPicked == 1) {
            totalPickedTxt.text = "${order.TotalLinesPicked} ${context.getString(R.string.picked_singular)}"
            totalPickedTxt.visibility = View.VISIBLE
        } else {
            totalPickedTxt.visibility = View.GONE
        }

        if (order.TotalLinesPaused > 1) {
            totalPausedTxt.text = "${order.TotalLinesPaused} ${context.getString(R.string.paused_plural)}"
            totalPausedTxt.visibility = View.VISIBLE
        } else if (order.TotalLinesPaused == 1) {
            totalPausedTxt.text = "${order.TotalLinesPaused} ${context.getString(R.string.paused_singular)}"
            totalPausedTxt.visibility = View.VISIBLE
        } else {
            totalPausedTxt.visibility = View.GONE
        }

        if (order.HeaderNotes.isEmpty()) {
            orderNotesTxt.visibility = View.GONE
        } else {
            orderNotesTxt.text = order.HeaderNotes
            orderNotesTxt.visibility = View.VISIBLE
        }

        val imgView = view

        if (position == GlobalVars.selectedItem) {
            if (order.FutureBilling2) {
                imgView.setBackgroundResource(R.drawable.item_selected_fut)
            } else if (order.FutureBilling) {
                imgView.setBackgroundResource(R.drawable.item_selected_special)
            } else if (order.OrderImportant) {
                imgView.setBackgroundResource(R.drawable.item_selected_imp)
            } else if (order.isRepo) {
                imgView.setBackgroundResource(R.drawable.item_selected_repo)
            } else if (order.isNewColor) {
                imgView.setBackgroundResource(R.drawable.item_selected_new)
            } else {
                imgView.setBackgroundResource(R.drawable.item_selected)
            }

            if (GlobalVars.headers.size == 1) {
                if (order.FutureBilling2) {
                    imgView.setBackgroundResource(R.drawable.item_selected_unique_fut)
                } else if (order.FutureBilling) {
                    imgView.setBackgroundResource(R.drawable.item_selected_unique_special)
                } else if (order.OrderImportant) {
                    imgView.setBackgroundResource(R.drawable.item_selected_unique_imp)
                } else if (order.isRepo) {
                    imgView.setBackgroundResource(R.drawable.item_selected_unique_repo)
                } else if (order.isNewColor) {
                    imgView.setBackgroundResource(R.drawable.item_selected_unique_new)
                } else {
                    imgView.setBackgroundResource(R.drawable.item_selected_unique)
                }
            } else if (position == 0) {
                Log.i("OrderAdapter", imgView.toString())
                if (order.FutureBilling2) {
                    imgView.setBackgroundResource(R.drawable.item_selected_first_fut)
                } else if (order.FutureBilling) {
                    imgView.setBackgroundResource(R.drawable.item_selected_first_special)
                } else if (order.OrderImportant) {
                    imgView.setBackgroundResource(R.drawable.item_selected_first_imp)
                } else if (order.isRepo) {
                    imgView.setBackgroundResource(R.drawable.item_selected_first_repo)
                } else if (order.isNewColor) {
                    imgView.setBackgroundResource(R.drawable.item_selected_first_new)
                } else {
                    imgView.setBackgroundResource(R.drawable.item_selected_first)
                }
            } else if (position == GlobalVars.headers.size - 1) {
                if (order.FutureBilling2) {
                    imgView.setBackgroundResource(R.drawable.item_selected_last_fut)
                } else if (order.FutureBilling) {
                    imgView.setBackgroundResource(R.drawable.item_selected_last_special)
                } else if (order.OrderImportant) {
                    imgView.setBackgroundResource(R.drawable.item_selected_last_imp)
                } else if (order.isRepo) {
                    imgView.setBackgroundResource(R.drawable.item_selected_last_repo)
                } else if (order.isNewColor) {
                    imgView.setBackgroundResource(R.drawable.item_selected_last_new)
                } else {
                    imgView.setBackgroundResource(R.drawable.item_selected_last)
                }
            }
        } else {

            if (order.FutureBilling2) {
                imgView.setBackgroundResource(R.drawable.item_fut)
            } else if (order.FutureBilling) {
                imgView.setBackgroundResource(R.drawable.item_special)
            } else if (order.OrderImportant) {
                imgView.setBackgroundResource(R.drawable.item_imp)
            } else if (order.isRepo) {
                imgView.setBackgroundResource(R.drawable.item_repo)
            } else if (order.isNewColor) {
                imgView.setBackgroundResource(R.drawable.item_new)
            } else {
                imgView.setBackgroundResource(R.drawable.item)
            }


            if (GlobalVars.headers.size == 1) {
                if (order.FutureBilling2) {
                    imgView.setBackgroundResource(R.drawable.item_unique_fut)
                } else if (order.FutureBilling) {
                    imgView.setBackgroundResource(R.drawable.item_unique_special)
                } else if (order.OrderImportant) {
                    imgView.setBackgroundResource(R.drawable.item_unique_imp)
                } else if (order.isRepo) {
                    imgView.setBackgroundResource(R.drawable.item_unique_repo)
                } else if (order.isNewColor) {
                    imgView.setBackgroundResource(R.drawable.item_unique_new)
                } else {
                    imgView.setBackgroundResource(R.drawable.item_unique)
                }
            } else if (position == 0) {
                if (order.FutureBilling2) {
                    imgView.setBackgroundResource(R.drawable.item_first_fut)
                } else if (order.FutureBilling) {
                    imgView.setBackgroundResource(R.drawable.item_first_special)
                } else if (order.OrderImportant) {
                    imgView.setBackgroundResource(R.drawable.item_first_imp)
                } else if (order.isRepo) {
                    imgView.setBackgroundResource(R.drawable.item_first_repo)
                } else if (order.isNewColor) {
                    imgView.setBackgroundResource(R.drawable.item_first_new)
                } else {
                    imgView.setBackgroundResource(R.drawable.item_first)
                }
            } else if (position == GlobalVars.headers.size - 1) {
                if (order.FutureBilling2) {
                    imgView.setBackgroundResource(R.drawable.item_last_fut)
                } else if (order.FutureBilling) {
                    imgView.setBackgroundResource(R.drawable.item_last_special)
                } else if (order.OrderImportant) {
                    imgView.setBackgroundResource(R.drawable.item_last_imp)
                } else if (order.isRepo) {
                    imgView.setBackgroundResource(R.drawable.item_last_repo)
                } else if (order.isNewColor) {
                    imgView.setBackgroundResource(R.drawable.item_last_new)
                } else {
                    imgView.setBackgroundResource(R.drawable.item_last)
                }
            }
        }

        if (order.HeaderStatusId.lowercase() == "k") {
            if (order.FutureBilling2) {
                imgView.setBackgroundResource(R.drawable.item_picked_fut)
            } else if (order.FutureBilling) {
                imgView.setBackgroundResource(R.drawable.item_picked_special)
            } else if (order.OrderImportant) {
                imgView.setBackgroundResource(R.drawable.item_picked_imp)
            } else if (order.isRepo) {
                imgView.setBackgroundResource(R.drawable.item_picked_repo)
            } else if (order.isNewColor) {
                imgView.setBackgroundResource(R.drawable.item_picked_new)
            } else {
                imgView.setBackgroundResource(R.drawable.item_picked)
            }


            if (GlobalVars.headers.size == 1) {
                if (order.FutureBilling2) {
                    imgView.setBackgroundResource(R.drawable.item_picked_unique_fut)
                } else if (order.FutureBilling) {
                    imgView.setBackgroundResource(R.drawable.item_picked_unique_special)
                } else if (order.OrderImportant) {
                    imgView.setBackgroundResource(R.drawable.item_picked_unique_imp)
                } else if (order.isRepo) {
                    imgView.setBackgroundResource(R.drawable.item_picked_repo)
                } else if (order.isNewColor) {
                    imgView.setBackgroundResource(R.drawable.item_picked_new)
                } else {
                    imgView.setBackgroundResource(R.drawable.item_picked_unique)
                }
            } else if (position == 0) {
                if (order.FutureBilling2) {
                    imgView.setBackgroundResource(R.drawable.item_picked_first_fut)
                } else if (order.FutureBilling) {
                    imgView.setBackgroundResource(R.drawable.item_picked_first_special)
                } else if (order.OrderImportant) {
                    imgView.setBackgroundResource(R.drawable.item_picked_first_imp)
                } else if (order.isRepo) {
                    imgView.setBackgroundResource(R.drawable.item_picked_first_repo)
                } else if (order.isNewColor) {
                    imgView.setBackgroundResource(R.drawable.item_picked_first_new)
                } else {
                    imgView.setBackgroundResource(R.drawable.item_picked_first)
                }
            } else if (position == GlobalVars.headers.size - 1) {
                if (order.FutureBilling2) {
                    imgView.setBackgroundResource(R.drawable.item_picked_last_fut)
                } else if (order.FutureBilling) {
                    imgView.setBackgroundResource(R.drawable.item_picked_last_special)
                } else if (order.OrderImportant) {
                    imgView.setBackgroundResource(R.drawable.item_picked_last_imp)
                } else if (order.isRepo) {
                    imgView.setBackgroundResource(R.drawable.item_picked_last_repo)
                } else if (order.isNewColor) {
                    imgView.setBackgroundResource(R.drawable.item_picked_last_new)
                } else {
                    imgView.setBackgroundResource(R.drawable.item_picked_last)
                }
            }

            takeOrderBtn.visibility = View.GONE
        }

        takeOrderBtn.setOnClickListener {
            GlobalVars.selectedOrder = position
            context.startActivity(Intent(context, ItemsActivity::class.java))

        }

        return view
    }

    fun getDuration(headerDate: String): String {
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

            val orderDateMS = dateFormat.parse(headerDate).time

            val pTime = PrettyTime(Locale(Locale.getDefault().language))

            // Calculate the precise durations between now and the order date
            val durations: List<Duration> = pTime.calculatePreciseDuration(Date(orderDateMS))

            // If more than one duration, remove the smallest unit
            val adjustedDurations = if (durations.size > 1) durations.dropLast(1) else durations

            return pTime.format(adjustedDurations)
        } catch (e: Exception) {
            Log.e("OrderAdapter", "Error parsing date: $e")
            return ""
        }
    }
}
