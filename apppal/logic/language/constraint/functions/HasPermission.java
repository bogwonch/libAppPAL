package apppal.logic.language.constraint.functions;

/* import android.content.ContextWrapper; */
/* import android.content.pm.ApplicationInfo; */
/* import android.content.pm.PackageInfo; */
/* import android.content.pm.PackageManager; */
/* import appguarden.apppal_checker.MainActivity; */

import apppal.logic.interfaces.ConstraintFunction;
import apppal.logic.language.constraint.CE;
import apppal.logic.language.constraint.Fail;
import java.util.List;

/** Check if a permission is present using a local server. */
public class HasPermission implements ConstraintFunction {
  @Override
  public int arity() {
    return 2;
  }

  @Override
  public CE eval(List<CE> args) {
    return new Fail();
    /* BufferedReader in = null; */
    /* try */
    /* { */
    /*   final String app = args.get(0).toString().replace("\"", ""); */
    /*   final String perm = args.get(1).toString().replace("\"", ""); */
    /*  */
    /*   // Check we are dealing with an app */
    /*   if (!app.startsWith("apk://")) */
    /*     return new Fail(); */
    /*  */
    /*   final String name = app.substring(6); */
    /*  */
    /*   if (MainActivity.instance == null) return new Fail(); */
    /*  */
    /*   final PackageManager pm = MainActivity.instance.getPackageManager(); // Such hack */
    /*   final PackageInfo info = pm.getPackageInfo(name, PackageManager.GET_PERMISSIONS); */
    /*   final String[] permissions = info.requestedPermissions; */
    /*  */
    /*   if (permissions != null) */
    /*     for (final String usesPermission : permissions) */
    /*       if (usesPermission.equals(perm)) return new Bool(true); */
    /*   return new Bool(false); */
    /* } */
    /* catch (Exception e) */
    /* { */
    /*   try */
    /*   { */
    /*     if (in != null) in.close();  // Already in failure code */
    /*   } */
    /*   catch (java.io.IOException err) {} */
    /*  */
    /*   return new Fail(); */
    /* } */
  }
}
