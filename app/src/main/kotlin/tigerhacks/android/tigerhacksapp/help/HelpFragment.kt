package tigerhacks.android.tigerhacksapp.help

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import tigerhacks.android.tigerhacksapp.R
import tigerhacks.android.tigerhacksapp.shared.fragments.BaseFragment

/**
 * @author pauldg7@gmail.com (Paul Gillis)
 */

//Helper data class to link View Id's to the Url that should open that that view Id is clicked
private data class IdLink(val id: Int, val link: String)

class HelpFragment : BaseFragment(R.layout.fragment_help) {
    override val navId = R.id.navigation_help
    override val titleResId = R.string.title_help

    override fun onViewCreated(layoutView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(layoutView, savedInstanceState)
        arrayOf(
            IdLink(R.id.tigerHacksWebsiteCardView, "http://tigerhacks.com/"),
            IdLink(R.id.mcaSlackCardView,"https://join.slack.com/t/tigerhacks2019/shared_invite/enQtNzg3ODQxMjQyNDg2LWExZTIyNWQ1ZThlMGRhMzAwNjQ4MGEwZDhhMmQxNTUwMTcyOGZiNjAxNzFkN2IzZjQxMDhhZGI5ZmFlMzkxMWQ"),
            IdLink(R.id.introFlaskCardView,"https://www.youtube.com/watch?v=V0QmmrTTbY4"),
            IdLink(R.id.introiOSCardView,"https://www.youtube.com/watch?v=kobP_rJAuyI"),
            IdLink(R.id.introWebDevCardView,"https://www.youtube.com/watch?v=KaNfsfwSUu4&t=66s"),
            IdLink(R.id.devPostCardView, "https://th19.devpost.com/"),
            IdLink(R.id.twitterCardView, "https://twitter.com/TigerHacksHD"),
            IdLink(R.id.instagramCardView, "https://instagram.com/tigerhacks"),
            IdLink(R.id.facebookCardView, "https://facebook.com/TigerHacks"),
            IdLink(R.id.introGithub, "https://www.youtube.com/watch?v=-k8B2w5X7qQ"),
            IdLink(R.id.introPython, "https://www.youtube.com/watch?v=HSq9JpflYiw"),
            IdLink(R.id.introDataSci, "https://www.youtube.com/watch?v=iWFpKD3VGsE")
        ).forEach { idLink ->
            //For Every IdLink register a click listener to the view id
            layoutView.findViewById<HelpItemView>(idLink.id).setOnClickListener {
                //When clicked pull Id's link and start a browse intent with that link
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(idLink.link)))
            }
        }
    }
}
