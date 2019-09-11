package tigerhacks.android.tigerhacksapp.resources

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import tigerhacks.android.tigerhacksapp.HomeScreenActivity
import tigerhacks.android.tigerhacksapp.R
import tigerhacks.android.tigerhacksapp.SplashActivity

//Helper data class to link View Id's to the Url that should open that that view Id is clicked
private data class IdLink(val id: Int, val link: String)

class ResourcesFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = ResourcesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_resources, container, false)
        arrayOf(
            IdLink(R.id.tigerHacksWebsiteCardView, "http://tiger-hacks.com/"),
            IdLink(R.id.mcaSlackCardView,"https://mizzoumca.slack.com/"),
            IdLink(R.id.introFlaskCardView,"https://www.youtube.com/watch?v=V0QmmrTTbY4"),
            IdLink(R.id.introiOSCardView,"https://www.youtube.com/watch?v=kobP_rJAuyI"),
            IdLink(R.id.introWebDevCardView,"https://www.youtube.com/watch?v=KaNfsfwSUu4&t=66s")
        ).forEach { idLink ->
            //For Every IdLink register a click listener to the view id
            view.findViewById<ResourceItemView>(idLink.id).setOnClickListener {
                //When clicked pull Id's link and start a browse intent with that link
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(idLink.link)))
            }
        }

        view.findViewById<ResourceItemView>(R.id.logOutButton).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(context, SplashActivity::class.java))
            activity?.finish()
        }
        return view
    }
}
