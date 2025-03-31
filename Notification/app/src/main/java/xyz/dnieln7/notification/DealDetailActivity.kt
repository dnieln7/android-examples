package xyz.dnieln7.notification

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import xyz.dnieln7.notification.databinding.ActivityDealDetailBinding

class DealDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDealDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val deal = intent.getSerializableExtra(Deal.INTENT_KEY) as Deal

        binding.name.text = deal.name
        binding.image.load(deal.image) {
            crossfade(true)
            error(R.drawable.ic_broken_image)
        }

        val oldPrice = deal.oldPrice.to2DecimalsString()
        val newPrice = deal.newPrice.to2DecimalsString()
        val getTheseFor = ContextCompat.getString(this, R.string.get_these_for)
        val dealString = "$getTheseFor $oldPrice $newPrice"

        val spannableDealString = SpannableString(dealString).apply {
            setSpan(
                StrikethroughSpan(),
                getTheseFor.indices.last + 2,
                getTheseFor.indices.last + 2 + oldPrice.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(Color.GRAY),
                getTheseFor.indices.last + 2,
                getTheseFor.indices.last + 2 + oldPrice.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
        }

        binding.dealPrice.text = spannableDealString
        binding.description.text = deal.description

        binding.exit.setOnClickListener { finish() }
    }
}